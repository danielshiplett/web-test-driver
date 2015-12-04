package org.brewman.web.test.navigator;

import org.brewman.web.test.driver.TestDriver;
import org.brewman.web.test.driver.WebTestException;
import org.brewman.web.test.view.IView;
import org.brewman.web.test.view.MainUnauthenticatedView;

public class MainViewUnauthenticatedNavigator extends AbstractViewNavigator {

    private String siteUrl;

    public void initialize() {
        siteUrl = "http://somesite.com";
    }

    public void loadSite()  {
        TestDriver.loadPage(siteUrl);
    }

    public void clickHomeLink()  {
        new MainUnauthenticatedView().clickHomeLink();
    }

    public void clickLoginLink()  {
        new MainUnauthenticatedView().clickLoginLink();
    }

    public void clickCreateAccountLink()  {
        new MainUnauthenticatedView().clickCreateAccountLink();
    }

    @Override
    public IView getView() {
        return new MainUnauthenticatedView();
    }
}
