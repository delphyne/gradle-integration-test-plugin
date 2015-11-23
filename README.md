[![Build Status](https://travis-ci.org/delphyne/gradle-test-suites.svg?branch=master)](https://travis-ci.org/delphyne/gradle-test-suites)

# test-suite-gradle-plugin
_A plugin to simplify the creation and management of test suites within a gradle build_

This plugin is not yet ready for public consumption.

## Goals

* Simpler configuration of multiple test suites within a single gradle build
* Unit test and coverage reports for each test set independently
* An aggregate test report
* Allow for pre-suite and post-suite hooks (for example, to stand up test dbs, etc.)
* Allow each test suite to identify if the should "fail the build" if there are errors
* Allow a simple means of configuring all test suites the same way (all use TestNG, for example)

## Installation

TODO : No released artifacts yet

## Usage

With no additional configuration, the plugin will automatically create a second test set for integration tests.  This
includes:

* A new configuration is created to hold dependencies
* The new configuration extends from the test configuration so you may use any helpers and libraries already in place
* A new task, integrationTest, to run your tests
* The integrationTest task is added to 'check' to fail the build if there are any failures.
* A new source dir(s), src/integration-test/{java,groovy} to contain your test classes
* A new report task to generate the standard HTML, XML, and text test reports
* An aggregate report task combines unit and integration test reports into a single html report.

## Configuration

TODO : defaults only right now
