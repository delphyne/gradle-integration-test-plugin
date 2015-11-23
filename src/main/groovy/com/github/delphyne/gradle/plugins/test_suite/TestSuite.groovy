package com.github.delphyne.gradle.plugins.test_suite

import groovy.transform.Canonical
import groovy.transform.ToString

/**
 *
 */
@Canonical
@ToString(includeNames = true, includePackage = false)
class TestSuite {

	/**
	 * The name of the test suite.  This informs the name of the task, configuration, and reports.
	 */
	String name

	/**
	 * If true (default), the task generated for this test suite will be added to the check task.
	 */
	boolean failBuild = true

	/**
	 * The location of the test sources.  By default, this is <pre>${project.buildDir}/src/test</pre>
	 */
	String sourceSet = 'test'

	/**
	 * The file name pattern that distinguishes tests of this type from others.  If a {@link #sourceSet} is unique,
	 * this defaults to <pre>**&#47;*</pre>.  If the sourceSet already exists, then defaults to the Camel-Cased version
	 * of your test suite name.  For example, integration-test becomes <pre>**&#47;*IntegrationTest</pre>.
	 */
	String pattern

	/**
	 * The test suite from which this test suite descends.  By default, 'test'.  Note:  While the compilation step will
	 * necessarily depend on the ancestor, the test task itself does not depend on the ancestor.  This allows you to
	 * work on tests in a descendant suite while there may still be failing tests in another.
	 */
	String extendsFrom

	TestSuite(String name) {
		this.name = name
	}

	def methodMissing(String name, args) {
		if (args.size() != 1) {
			throw new Exception("")
		}

		setProperty(name, args[0])
	}
}
