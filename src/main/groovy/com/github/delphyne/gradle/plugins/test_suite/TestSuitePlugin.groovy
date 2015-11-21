package com.github.delphyne.gradle.plugins.test_suite

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.testing.Test
import org.gradle.testing.jacoco.plugins.JacocoPlugin

import static TestTaskFactory.patternTestTask
import static TestTaskFactory.sourceSetTestTask

/**
 * A Plugin which eases the configuration of multiple test suites
 */
class TestSuitePlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {
		/**
		 * Set up our extension/convention object
		 */
		project.plugins.apply(JavaPlugin)
		def testSuites = project.container(TestSuite)
		project.extensions.testSuites = testSuites

		/**
		 * NamedDomainObjectContainer can fire events every time it encounters one of our conventions
		 * via the all method, but it does so before the convention has been fully resolved.  In order
		 * to see the final version, we need to bind to the afterEvaluate phase.  The
		 * downside to this is it makes it hard for users of our plugin to affect the tasks and
		 * sourceSets we create.
		 */
		project.afterEvaluate {
			Task check = project.tasks.getByName('check')
			Test test = project.tasks.getByName('test') as Test
			Test precedingTestSuite = test
			(testSuites ?: [new TestSuite('integration')]).each { TestSuite ts ->
				project.logger.info("Processing ${ts.name} test suite with configuration: ${ts}")
				Test t = ts.seperateSourceSet ? sourceSetTestTask(project, ts, precedingTestSuite) : patternTestTask(project, ts, precedingTestSuite)
				precedingTestSuite = t

				if (ts.failBuild) {
					check.dependsOn t
				}

				if (project.plugins.hasPlugin(JacocoPlugin)) {

				}
			}
		}
	}
}
