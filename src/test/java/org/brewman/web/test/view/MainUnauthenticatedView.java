package org.brewman.web.test.view;

import org.brewman.web.test.container.IContainer;
import org.brewman.web.test.container.MainUnauthenticatedViewContainer;
import org.brewman.web.test.driver.TestDriver;
import org.brewman.web.test.driver.WebTestException;
import org.openqa.selenium.support.PageFactory;

public class MainUnauthenticatedView extends UnauthenticatedView {
    public IContainer getViewContainer()  {
        return PageFactory.initElements(TestDriver.getCurrentDriver(),
                MainUnauthenticatedViewContainer.class);
    }
}
