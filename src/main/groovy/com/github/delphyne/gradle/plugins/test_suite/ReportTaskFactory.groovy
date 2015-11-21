package com.github.delphyne.gradle.plugins.test_suite

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.plugins.ProjectReportsPluginConvention
import org.gradle.api.tasks.testing.Test
import org.gradle.testing.jacoco.tasks.JacocoReport

abstract class ReportTaskFactory {
	private ReportTaskFactory() {}

	static Task jacocoReportTask(Project project, TestSuite testSuite, Test test) {
		def javaConvention = project.convention.getPlugin(JavaPluginConvention)
		def reportsConvention = project.convention.getPlugin(ProjectReportsPluginConvention)
		project.tasks.create("jacoco${testSuite.name.capitalize()}TestReport", JacocoReport).with {
			description = "Generate a code coverage report for the ${testSuite.name} tests."
			group = "reporting"
			dependsOn = ["${test.name}Classes"]
			sourceDirectories = javaConvention.sourceSets.main.java
			classDirectories = javaConvention.sourceSets.main.output
			executionData new File(project.buildDir, "jacoco/${test.name}.exec")
			project.reports.html.destination = new File(reportsConvention.projectReportDir, "jacoco/${testSuite.name}")
			test.finalizedBy it
			it
		}
	}
}
