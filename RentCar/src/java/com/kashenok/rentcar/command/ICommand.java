package com.kashenok.rentcar.command;

import com.kashenok.rentcar.exception.CommandException;
import javax.servlet.http.HttpServletRequest;

/**
 * The Interface ICommand. Base interface for all Commands.
 */
public interface ICommand {
    
    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    
    String execute (HttpServletRequest request) throws CommandException;
    
}
