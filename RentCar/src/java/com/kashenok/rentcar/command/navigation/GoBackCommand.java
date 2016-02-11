package com.kashenok.rentcar.command.navigation;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import javax.servlet.http.HttpServletRequest;

/**
 * The class GoBackCommand. Returns application user to the main page from error page.
 */
public class GoBackCommand implements ICommand {

    public static final String WELCOME_TEXT = "welcomeText";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
//        request.getSession().setAttribute(WELCOME_TEXT, "text.main.page");
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
