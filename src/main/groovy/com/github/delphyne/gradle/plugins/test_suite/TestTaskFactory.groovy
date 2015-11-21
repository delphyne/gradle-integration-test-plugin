package com.github.delphyne.gradle.plugins.test_suite

import org.gradle.api.Project
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.plugins.ProjectReportsPluginConvention
import org.gradle.api.tasks.testing.Test

abstract class TestTaskFactory {
	private TestTaskFactory() {}

	/**
	 * Creates a new Test task which will execute tests matching {@link TestSuite#pattern} and excludes tests matching
	 * this pattern from the default 'test' task.
	 * @param project The project to which the task will be added.
	 * @param testSuite The TestSuite for which we are generating a Test task.
	 * @param precedingTestTask The test task that was generated prior to this task.  This guarantees execution order
	 * 	will be the same in which they are defined, but does not create a task-level dependency.
	 * @return The newly created test task.
	 */
	static Test patternTestTask(Project project, TestSuite testSuite, Test precedingTestTask) {
		(project.tasks.findByName(JavaPlugin.TEST_TASK_NAME) as Test).exclude testSuite.pattern

		testTask(project, testSuite, precedingTestTask)
				.include(testSuite.pattern)
	}

	static Test sourceSetTestTask(Project project, TestSuite testSuite, Test precedingTestTask) {
		def javaConvention = project.convention.getPlugin(JavaPluginConvention)
		def newSourceSet = javaConvention.sourceSets.create(sourceSetName(testSuite.name)) {
			if (testSuite.dependsOnSuite) {
				def dependsOnSourceSet = sourceSetName(testSuite.dependsOnSuite)
				it.compileClasspath += javaConvention.sourceSets.getByName(dependsOnSourceSet).runtimeClasspath
				it.runtimeClasspath += javaConvention.sourceSets.getByName(dependsOnSourceSet).runtimeClasspath
			}
		}
		testTask(project, testSuite, precedingTestTask).with {
			testClassesDir = newSourceSet.output.classesDir
			classpath = newSourceSet.runtimeClasspath
			it
		}
	}

	private static Test testTask(Project project, TestSuite testSuite, Test precedingTestTask) {
		def reportsConvention = project.convention.getPlugin(ProjectReportsPluginConvention)
		project.tasks.create("${testSuite.name}${JavaPlugin.TEST_TASK_NAME.capitalize()}", Test).with {
			description = "Runs the ${testSuite.name.capitalize()} tests."
			group = JavaBasePlugin.VERIFICATION_GROUP
			reports.html.destination = new File(reportsConvention.projectReportDir, name)
			shouldRunAfter precedingTestTask
			it
		}
	}

	private static String sourceSetName(String testSuiteName) {
		testSuiteName.endsWith(JavaPlugin.TEST_TASK_NAME) ? testSuiteName : "${testSuiteName}-${JavaPlugin.TEST_TASK_NAME}"
	}
}
