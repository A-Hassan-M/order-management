package com.qeema.practicaltest.ordermanagement.bdds.steps.config;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // Path to your feature files
        glue = "com.qeema.practicaltest.ordermanagement.bdds.steps" // Package where step definitions are located
)
public class CucumberTestRunner extends CucumberSpringConfiguration {
}