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
class TestSuitesPlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {
		project.extensions.create(TestSuitesExtension.NAME, TestSuitesExtension)

		/*
		 * While the extension itself is registered within our apply method, the closure, if it exists, isn't evaluated
		 * until the project has been evaluatated.
		 */
		project.afterEvaluate {
			TestSuitesExtension extension = project.extensions.findByType(TestSuitesExtension)
			(extension.testSuites ?: [new TestSuite("integration-test")]).each {

			}
		}
	}
}
