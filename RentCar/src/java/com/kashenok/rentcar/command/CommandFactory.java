package com.kashenok.rentcar.command;

import com.kashenok.rentcar.command.navigation.UnhandledCommand;
import com.kashenok.rentcar.exception.CommandException;
import javax.servlet.http.HttpServletRequest;

/**
 * The class CommandFactory. Create objects of concrete commands.
 */
public class CommandFactory {
    
        private static final String COMMAND = "command";


    /**
     * The method defines the required command
     *
     * @param request is the request
     * @return current command
     * @throws CommandException
     */
    public ICommand defineCommand(HttpServletRequest request) throws CommandException {
        ICommand current = new UnhandledCommand();
        String action = request.getParameter(COMMAND);
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException ex) {
            throw new CommandException("Impossible to get List of orders in ShowUserOrderListCommand: ", ex);
        }
        return current;
    }

}
