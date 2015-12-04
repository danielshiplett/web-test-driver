package org.brewman.web.test.steps;

import java.net.MalformedURLException;

import org.brewman.web.test.driver.TestDriver;
import org.brewman.web.test.driver.WebTestException;
import org.brewman.web.test.navigator.NaviagtorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

public class SetupSteps {
    private static final Logger log = LoggerFactory.getLogger(SetupSteps.class);

    @Before("@web-01-bidding")
    public void setUp(Scenario scenario) throws MalformedURLException {
        log.info("Site Scenario Setup");

        TestDriver.setScenario(scenario);
    }

    @After("@web-01-bidding")
    public void tearDown(Scenario scenario) {
        log.info("Site Scenario Tear Down");

        if (scenario.isFailed()) {
            log.warn("Site Scenario Failed");

            /**
             * Take a screenshot and embed it in the report.
             */
            TestDriver.takeScreenshot();
        }
    }

    @Given("^I navigate to the site$")
    public void given_I_navigate_to_the_bidding_site()  {
        NaviagtorFactory.getMainUnauthenticatedViewNavigator().loadSite();
    }

    @And("^I click the Home link$")
    public void and_I_click_the_Home_link()  {
        NaviagtorFactory.getMainUnauthenticatedViewNavigator().clickHomeLink();
    }

    @And("^I click the Login link$")
    public void and_I_click_the_Login_link()  {
        NaviagtorFactory.getMainUnauthenticatedViewNavigator().clickLoginLink();
    }

    @And("^I click the Create Account link$")
    public void and_I_click_the_Create_Account_link()  {
        NaviagtorFactory.getMainUnauthenticatedViewNavigator()
                .clickCreateAccountLink();
    }
}
