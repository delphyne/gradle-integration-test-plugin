package com.github.delphyne.gradle.plugins.test_suite

import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeNames = true, includePackage = false)
class TestSuite {
	String name
	boolean failBuild
	boolean seperateSourceSet
	String pattern
	String dependsOnSuite

	TestSuite(String name) {
		this.name = name
		pattern = "**/*${name.capitalize()}Test*"
	}
}
