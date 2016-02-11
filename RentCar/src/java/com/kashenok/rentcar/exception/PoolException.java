package com.kashenok.rentcar.exception;

/**
 * The class PoolException. Create exception objects
 */
public class PoolException extends Exception {

    /**
     * The method generate PoolException without parameters
     */
    public PoolException() {
    }

    /**
     * The method generate PoolException with String parameter
     *
     * @param message
     */
    public PoolException(String message) {
        super(message);
    }

    /**
     * The method generate PoolException with String and exception cause
     * parameter
     *
     * @param message
     * @param cause
     */
    public PoolException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The method generate PoolException with exception cause parameter
     *
     * @param cause
     */
    public PoolException(Throwable cause) {
        super(cause);
    }

}
