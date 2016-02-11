package com.kashenok.rentcar.exception;

/**
 *The class ServiceException. Create exception objects
 */
public class ServiceException extends Exception {

    /**
     *The method generate ServiceException without parameters
     */
    public ServiceException() {
    }

    /**
     *The method generate ServiceException with String parameter
     * @param message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     *The method generate ServiceException with String and exception cause parameter
     * @param message
     * @param cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The method generate ServiceException with exception cause parameter
     *
     * @param cause
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
    
}
