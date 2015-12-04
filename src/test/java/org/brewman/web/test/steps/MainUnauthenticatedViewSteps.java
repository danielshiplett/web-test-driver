package org.brewman.web.test.steps;

import org.brewman.web.test.navigator.NaviagtorFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class MainUnauthenticatedViewSteps {

    @Then("^I should see all the required Main Unauthenticated View elements$")
    public void i_should_see_all_the_required_Main_Unauthenticated_View_elements()
            throws Throwable {
        NaviagtorFactory.getMainUnauthenticatedViewNavigator()
                .checkRequiredElements();
    }

    @Given("^I have navigated to the Create Account page$")
    public void i_have_navigated_to_the_Create_Account_page() throws Throwable {
        NaviagtorFactory.getMainUnauthenticatedViewNavigator().loadSite();
        NaviagtorFactory.getMainUnauthenticatedViewNavigator()
                .checkRequiredElements();
        NaviagtorFactory.getMainUnauthenticatedViewNavigator()
                .clickCreateAccountLink();
        // NaviagtorFactory.getCreateAccountViewNavigator()
        // .checkRequiredElements();
    }
}
