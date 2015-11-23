package com.github.delphyne.gradle.plugins.test_suite

class TestSuitesExtension {
	final static String NAME = 'testSuites'
	List<TestSuite> testSuites = []

	def methodMissing(String name, args) {
		def suite = new TestSuite(name)
		if (args.size() != 1) {
			throw new Exception("Unexpected number of arguments in testSuite configuration.")
		}
		def closure = args[0]
		if (!(closure instanceof Closure)) {
			throw new Exception("Expected closure.")
		}
		closure.resolveStrategy = Closure.DELEGATE_FIRST
		closure.delegate = suite
		closure()
		testSuites << suite
	}
}
