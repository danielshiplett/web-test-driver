package org.brewman.web.test.navigator;

import org.brewman.web.test.driver.WebTestException;
import org.brewman.web.test.view.IView;

/**
 * 
 * @author danielshiplett
 */
public interface ViewNavigator {

    IView getView();

    void checkRequiredElements() ;
}
