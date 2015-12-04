package org.brewman.web.test.driver;

/**
 * Catch all StaleElementExcetions and wrap them in this.
 * 
 * @author ddshipl
 */
public class WebTestStaleElementException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -5091924210293421154L;

    public WebTestStaleElementException(String message) {
        super(message);
    }

    public WebTestStaleElementException(Throwable cause) {
        super(cause);
    }

    public WebTestStaleElementException(String message, Throwable cause) {
        super(message, cause);
    }
}
