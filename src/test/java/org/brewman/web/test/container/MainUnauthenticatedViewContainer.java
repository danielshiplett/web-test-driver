package org.brewman.web.test.container;

import org.brewman.web.test.driver.TestDriver;
import org.brewman.web.test.driver.WebTestException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainUnauthenticatedViewContainer implements IContainer {

    @FindBy(id = "some-main-content")
    private WebElement someMainContent;

    @Override
    public void checkRequiredElementsExist()  {
        TestDriver.waitForElement(someMainContent);
    }
}
