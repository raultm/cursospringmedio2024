package es.acaex.cursospringmedio2024;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources",
    plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class TestConfig {
}
