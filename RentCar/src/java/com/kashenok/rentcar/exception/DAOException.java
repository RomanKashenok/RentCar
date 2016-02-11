package com.kashenok.rentcar.exception;

/**
 * The class DAOException. Create exception objects
 */
public class DAOException extends Exception {

    /**
     * The method generate DAOException without parameters
     */
    public DAOException() {
    }

    /**
     * The method generate DAOException with String parameter
     *
     * @param message
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * The method generate DAOException with String and exception cause
     * parameter
     *
     * @param message
     * @param cause
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The method generate DAOException with exception cause parameter
     *
     * @param cause
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

}
