package org.brewman.web.test.container;

import org.brewman.web.test.driver.TestDriver;
import org.brewman.web.test.driver.WebTestException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UnauthenticatedNavbarContainer implements IContainer {

    @FindBy(id = "navbar-home-link")
    private WebElement navbarHomeLink;

    @FindBy(id = "navbar-create-account-link")
    private WebElement navbarCreateAccountLink;

    @FindBy(id = "navbar-login-link")
    private WebElement navbarLoginLink;

    @Override
    public void checkRequiredElementsExist()  {
        TestDriver.waitForElement(navbarHomeLink);
        TestDriver.waitForElement(navbarCreateAccountLink);
        TestDriver.waitForElement(navbarLoginLink);
    }

    public void clickHomeLink() {
        navbarHomeLink.click();
    }

    public void clickCreateAccountLink() {
        navbarCreateAccountLink.click();
    }

    public void clickLoginLink() {
        navbarLoginLink.click();
    }
}
