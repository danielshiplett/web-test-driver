package org.brewman.web.test.driver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.pagefactory.DefaultElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Scenario;

/**
 * The mega-do-everything-test-driver.
 * 
 * - It gets us a connection to the right WebDriver!
 * 
 * - It keeps test state!!
 * 
 * - It converts exceptions into something useful!!!
 * 
 * @author danielshiplett
 */
public class TestDriver {
    private static Logger LOG = LoggerFactory.getLogger(TestDriver.class);

    private static Scenario scenario;
    private static final boolean isRemote = true;

    private static WebDriver mDriver;
    private static Thread shutdownHook;

    public static Scenario getScenario() {
        return scenario;
    }

    public static void setScenario(Scenario scenario) {
        TestDriver.scenario = scenario;

        LOG.info("Scenario Name: \"{}\"", scenario.getName());
        LOG.info("Scenario ID: \"{}\"", scenario.getId());
        LOG.info("Scenario Tags: \"{}\"", scenario.getSourceTagNames());
    }

    /**
     * Call this to get a new instance of the WebDriver. The new instance will
     * register a shutdown hook that will clean up the WebDriver when the JVM
     * terminates. This will also cleanup after any previous WebDriver that was
     * initialize.
     * 
     * @return
     */
    public synchronized static WebDriver getDriver() {
        try {
            if (mDriver != null) {
                LOG.warn("Resetting WebDriver");
                mDriver.quit();

                if (shutdownHook != null) {
                    Runtime.getRuntime().removeShutdownHook(shutdownHook);
                }
            }

            mDriver = getNewDriver();
        } catch (MalformedURLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            shutdownHook = new Thread(() -> {
                quitDriver();
            });

            Runtime.getRuntime().addShutdownHook(shutdownHook);
        }

        return mDriver;
    }

    /**
     * Call this when you need a reference to the current WebDriver. If none
     * exists then one will be created.
     * 
     * @return
     * 
     */
    public synchronized static WebDriver getCurrentDriver() {
        if (mDriver == null) {
            mDriver = getDriver();
        }

        return mDriver;
    }

    private static WebDriver getNewDriver() throws MalformedURLException,
            WebTestException {
        LOG.warn("Initializing Test Driver");

        WebDriver rtn = null;

        if (isRemote) {
            LOG.trace("going remote");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("-incognito");

            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            capabilities.setJavascriptEnabled(true);

            logCapabilities(capabilities);

            try {
                rtn = new RemoteWebDriver(new URL("http://localhost:9515"),
                        capabilities);
            } catch (UnreachableBrowserException e) {
                // LOG.error(e.getMessage());
                throw new WebTestException(
                        "Could not connect to remote WebDriver");
            }
        } else {
            LOG.trace("staying local");

            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setJavascriptEnabled(true);

            logCapabilities(capabilities);

            rtn = new FirefoxDriver(capabilities);
        }

        return rtn;
    }

    /**
     * Clean up after the current WebDriver.
     */
    public synchronized static void quitDriver() {
        LOG.warn("Quitting Test Driver");

        try {
            if (mDriver != null) {
                mDriver.quit();
                mDriver = null;
            } else {
                LOG.info("No Test Driver To Quit");
            }
        } catch (UnreachableBrowserException e) {
            LOG.error(e.getMessage(), e);
            LOG.warn("cannot close browser: unreachable browser");
        }
    }

    public static void takeScreenshot() {
        LOG.warn("Taking Screenshot");
        // WebDriver augmentedDriver = new Augmenter().augment(mDriver);
        // final File screenshot = ((TakesScreenshot) augmentedDriver)
        // .getScreenshotAs(OutputType.FILE);

        // scenario.embed(screenshot, "image/png");
    }

    private static void logCapabilities(Capabilities c) {
        LOG.info("Capability Platform: {}", c.getPlatform());
        LOG.info("Capability Browser: {}", c.getBrowserName());
        LOG.info("Capability Version: {}", c.getVersion());
    }

    /**
     * Calls to this will trigger a new WebDriver to be loaded. This will cause
     * any previously created WebDriver to be quit and to have any shutdown
     * hooks registered for it to be removed.
     * 
     * @param url
     * 
     */
    public static void loadPage(String url) {
        LOG.info("Directing Browser To: {}", url);
        getDriver().get(url);
    }

    public static void fail(String message) {
        LOG.error(message);
        org.junit.Assert.fail(message);
    }

    public static void selectByVisibleText(WebElement element,
            String visibleText) {
        try {
            Select select = new Select(element);
            select.selectByVisibleText(visibleText);
        } catch (NoSuchElementException e) {
            try {
                throw new WebTestException(
                        convertExceptionToUsefulErrorMessage(e, element));
            } catch (NoSuchFieldException | SecurityException
                    | IllegalArgumentException | IllegalAccessException e1) {
                throw new WebTestException(
                        "Could not find all required elements");
            }
        }
    }

    /**
     * Test that the WebElement is displayed.
     * 
     * NOTE: This function does not have support for catching the
     * StaleElementException.
     * 
     * @param toCheck
     *            the WebElement to test
     * 
     * @return the WebElement that was tested if it is displayed. Otherwise a
     *         RuntimeException will be thrown.
     * 
     */
    public static WebElement isDisplayed(WebElement toCheck) {
        try {
            if (!toCheck.isDisplayed()) {
                return null;
            }
        } catch (NoSuchElementException e) {
            try {
                throw new WebTestException(
                        convertExceptionToUsefulErrorMessage(e, toCheck));
            } catch (NoSuchFieldException | SecurityException
                    | IllegalArgumentException | IllegalAccessException e1) {
                throw new WebTestException(
                        "Could not find all required elements");
            }
        }

        return toCheck;
    }

    public static WebElement waitForElement(WebElement elementToWaitFor) {
        return waitForElement(elementToWaitFor, null);
    }

    public static WebElement waitForElement(WebElement elementToWaitFor,
            Integer waitTimeInSeconds) {
        if (waitTimeInSeconds == null) {
            // TODO Make this configurable
            waitTimeInSeconds = 10;
        }

        WebDriverWait wait = new WebDriverWait(getCurrentDriver(),
                waitTimeInSeconds);

        WebElement rtn = null;

        try {
            rtn = wait.until(ExpectedConditions.visibilityOf(elementToWaitFor));
        } catch (TimeoutException e) {
            try {
                throw new WebTestException(
                        convertExceptionToUsefulErrorMessage(e,
                                elementToWaitFor));
            } catch (NoSuchFieldException | SecurityException
                    | IllegalArgumentException | IllegalAccessException e1) {
                throw new WebTestException(
                        "Could not find all required elements");
            }
        } catch (StaleElementReferenceException e) {
            String message;

            try {
                message = convertExceptionToUsefulErrorMessage(e,
                        elementToWaitFor);
            } catch (NoSuchFieldException | SecurityException
                    | IllegalArgumentException | IllegalAccessException e1) {
                throw new WebTestException(
                        "Could not find all required elements");
            }

            LOG.warn(message);
            throw new WebTestStaleElementException(message);
        }

        return rtn;
    }

    private static String convertExceptionToUsefulErrorMessage(Exception e,
            WebElement element) throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        InvocationHandler ih = Proxy.getInvocationHandler(element);
        LOG.trace("ih.class: {}", ih.getClass().getName());

        if (ih instanceof LocatingElementHandler) {
            LocatingElementHandler leh = (LocatingElementHandler) ih;

            ElementLocator locator = (ElementLocator) getPrivateFieldFrom(leh,
                    "locator");
            LOG.trace("locator.class: {}", locator.getClass().getName());

            if (locator instanceof DefaultElementLocator) {
                DefaultElementLocator del = (DefaultElementLocator) locator;

                By by = (By) getPrivateFieldFrom(del, "by");
                LOG.trace("by.class: {}", by.getClass().getName());

                if (by instanceof By.ById) {
                    By.ById byId = (By.ById) by;

                    String id = (String) getPrivateFieldFrom(byId, "id");

                    LOG.error("ID: {}", id);

                    // TODO: We may not always search by ID.
                    String simpleName = e.getClass().getSimpleName();
                    return simpleName + " while locating element with ID \""
                            + id + "\"";
                }
            }
        }

        return null;
    }

    private static Object getPrivateFieldFrom(Object obj, String fieldName)
            throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        Field rtnField = obj.getClass().getDeclaredField(fieldName);
        rtnField.setAccessible(true);
        return rtnField.get(obj);
    }

    public static WebElement getParent(WebElement element) {
        return element.findElement(By.xpath(".."));
    }

    public static List<WebElement> getDropDownOptions(WebElement webElement) {
        Select select = new Select(webElement);
        return select.getOptions();
    }

    public static WebElement getDropDownOption(WebElement webElement,
            String value) {
        WebElement option = null;
        List<WebElement> options = getDropDownOptions(webElement);
        for (WebElement element : options) {
            if (element.getAttribute("value").equalsIgnoreCase(value)) {
                option = element;
                break;
            }
        }
        return option;
    }

    public static void assertElementText(WebElement element, String expected) {
        try {
            assertNotNull(element);
            assertEquals(expected, element.getText());
        } catch (AssertionError e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    public static void assertInputText(WebElement element, String expected) {
        try {
            assertNotNull(element);
            assertEquals(expected, element.getAttribute("value"));
        } catch (AssertionError e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    public static String getUrlBar() {
        return getCurrentDriver().getCurrentUrl();
    }

    public static void callWithRetries(Runnable runnable, int maxRetries,
            int sleepTime) {
        LOG.debug("callWithRetries");

        int count = 0;
        boolean rtn = false;
        Exception lastException = null;

        while (count < maxRetries && rtn == false) {
            try {
                runnable.run();
                rtn = true;
            } catch (Exception e) {
                lastException = e;
                LOG.warn(e.getMessage());
            }

            count++;

            if (rtn == false && count < maxRetries) {
                LOG.warn("retry: {}", count);

                if (sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        LOG.warn(e.getMessage());
                    }
                }
            }
        }

        if (rtn == false) {
            if (lastException != null) {
                LOG.error(lastException.getMessage());
                throw new WebTestException(lastException.getMessage(),
                        lastException);
            } else {
                throw new WebTestException("Failed to execute call");
            }
        }
    }
}
