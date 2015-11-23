package com.github.delphyne.gradle.plugins.test_suite

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

@Test
class TestSuitesPluginIntegrationTest {

	File projectDir
	GradleRunner runner

	@BeforeMethod
	void setup() {
		List<File> pluginClasspath = getClass()
				.classLoader
				.findResource('plugin-classpath.txt')
				.readLines()
				.collect { new File(it) }
		projectDir = File.createTempDir()
		runner = GradleRunner
				.create()
				.withProjectDir(projectDir)
				.withPluginClasspath(pluginClasspath)
	}

	private void copyProject(String source) {
		new AntBuilder().copy(todir: projectDir.canonicalPath) {
			fileset(dir: "src/test/resources/${source}")
		}
	}

	public void testZeroConfig() {
		copyProject('zeroconfig')
		BuildResult result = runner
				.withArguments('tasks')
				.build()
		println result
	}
}
