package org.brewman.web.test.view;

import java.util.HashMap;
import java.util.Map;

import org.brewman.web.test.container.IContainer;
import org.brewman.web.test.driver.WebTestException;
import org.brewman.web.test.driver.WebTestStaleElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author danielshiplett
 */
public abstract class AbstractView implements IView {
    private static final Logger LOG = LoggerFactory
            .getLogger(AbstractView.class);

    private Map<String, IContainer> containers = new HashMap<String, IContainer>();

    public void checkRequiredElements()  {
        LOG.debug("checkRequiredElements");

        int count = 0;
        boolean rtn = false;
        Exception lastException = null;

        while (count < 3 && rtn == false) {
            try {
                rtn = doCheckRequiredElements();
            } catch (WebTestStaleElementException e) {
                lastException = e;
                LOG.warn(e.getMessage());
            }

            count++;

            if (rtn == false) {
                LOG.warn("Stale element exception retry: {}", count);
            }
        }

        if (rtn == false) {
            if (lastException != null) {
                LOG.error(lastException.getMessage());
            } else {
                throw new WebTestStaleElementException(
                        "Could not find all required elements!!!");
            }
        }
    }

    private boolean doCheckRequiredElements()  {
        for (Map.Entry<String, IContainer> entry : containers.entrySet()) {
            LOG.trace("doCheckRequiredElements: {}", entry.getKey());
            entry.getValue().checkRequiredElementsExist();
        }

        return true;
    }
}
