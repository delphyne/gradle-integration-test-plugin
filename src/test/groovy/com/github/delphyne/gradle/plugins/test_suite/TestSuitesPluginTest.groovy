package com.github.delphyne.gradle.plugins.test_suite

import org.gradle.api.internal.project.AbstractProject
import org.gradle.testfixtures.ProjectBuilder
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

@Test
class TestSuitesPluginTest {
	AbstractProject project

	@BeforeMethod
	void setup() {
		project = (AbstractProject) ProjectBuilder.builder().build()
	}

	void testExtension() {
		def extension = project.extensions.create(TestSuitesExtension.NAME, TestSuitesExtension)
		project.testSuites {
			a {
				failBuild false
				sourceSet 'foo'
				extendsFrom 'bar'
			}
			b {}
		}
		project.evaluate()
		assert 2 == extension.testSuites.size()

		TestSuite a = extension.testSuites.find { it.name == 'a' }
		assert a != null
		assert ! a.failBuild
		assert a.sourceSet == 'foo'
		assert a.extendsFrom == 'bar'

		assert extension.testSuites.find { it.name == 'b' }
	}
}
