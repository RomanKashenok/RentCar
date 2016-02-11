package com.kashenok.rentcar.exception;

/**
 * The class CommandException. Create exception objects
 */
public class CommandException extends Exception {

    /**
     * The method generate CommandException without parameters
     */
    public CommandException() {
    }

    /**
     * The method generate CommandException with String parameter
     *
     * @param message
     */
    public CommandException(String message) {
        super(message);
    }

    /**
     * The method generate CommandException with String and exception cause
     * parameter
     *
     * @param message
     * @param cause
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The method generate CommandException with exception cause parameter
     *
     * @param cause
     */
    public CommandException(Throwable cause) {
        super(cause);
    }

}
