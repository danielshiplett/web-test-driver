package org.brewman.web.test.navigator;

import org.brewman.web.test.driver.WebTestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractViewNavigator implements ViewNavigator {
    private static final Logger LOG = LoggerFactory
            .getLogger(AbstractViewNavigator.class);

    /**
     * All ViewNavigators must be able to check that all required elements are
     * present. Delegate this to the View of this ViewNavigator.
     * 
     * 
     */
    @Override
    public void checkRequiredElements()  {
        LOG.debug("checkRequiredElements");
        getView().checkRequiredElements();
    }
}
