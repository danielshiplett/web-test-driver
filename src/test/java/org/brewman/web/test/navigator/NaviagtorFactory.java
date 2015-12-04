package org.brewman.web.test.navigator;

public class NaviagtorFactory {

    private final static MainViewUnauthenticatedNavigator mainUnauthenticatedViewNavigator = new MainViewUnauthenticatedNavigator();

    public static MainViewUnauthenticatedNavigator getMainUnauthenticatedViewNavigator() {
        return mainUnauthenticatedViewNavigator;
    }
}
