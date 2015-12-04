package org.brewman.web.test;

import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@Component
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", plugin = { "pretty",
        "json:target/cucumber.json", "html:target/cucumber-html-report",
        "junit:target/cucumber-junit-report.xml" }, tags = { "@web-site",
        "@0000", "@functional" })
public class T_0000_F_Test {
}
