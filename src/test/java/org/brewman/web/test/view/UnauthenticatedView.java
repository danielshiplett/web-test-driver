package org.brewman.web.test.view;

import org.brewman.web.test.container.IContainer;
import org.brewman.web.test.container.UnauthenticatedNavbarContainer;
import org.brewman.web.test.driver.TestDriver;
import org.brewman.web.test.driver.WebTestException;
import org.openqa.selenium.support.PageFactory;

public abstract class UnauthenticatedView extends AbstractView {

    public IContainer getNavbarContainer()  {
        return getUnauthenticatedNavbarContainer();
    }

    private static UnauthenticatedNavbarContainer getUnauthenticatedNavbarContainer()
             {
        return PageFactory.initElements(TestDriver.getCurrentDriver(),
                UnauthenticatedNavbarContainer.class);
    }

    public void clickHomeLink()  {
        getUnauthenticatedNavbarContainer().clickHomeLink();
    }

    public void clickLoginLink()  {
        getUnauthenticatedNavbarContainer().clickLoginLink();
    }

    public void clickCreateAccountLink()  {
        getUnauthenticatedNavbarContainer().clickCreateAccountLink();
    }
}
