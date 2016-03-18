package service;

/**
 * Exception for errors in the service layer
 */
public class ServiceException extends Exception {

    public ServiceException(String s, Exception e) {
        super(s, e);
    }
}
