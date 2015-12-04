package org.brewman.web.test.driver;

/**
 * Catch all driver exceptions and wrap them in this.
 * 
 * @author ddshipl
 */
public class WebTestException extends RuntimeException {

    private static final long serialVersionUID = 4456071133452324169L;

    public WebTestException(String message) {
        super(message);
    }

    public WebTestException(Throwable cause) {
        super(cause);
    }

    public WebTestException(String message, Throwable cause) {
        super(message, cause);
    }
}
