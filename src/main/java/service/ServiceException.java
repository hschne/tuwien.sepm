package service;

import dao.DaoException;

/**
 * Exception for errors in the service layer
 */
public class ServiceException extends Exception {

    public ServiceException(String s, Exception e) {
        super(s, e);
    }

    public ServiceException(DaoException e) {
        super(e);
    }

    public ServiceException(String s) {
        super(s);
    }
}
