package com.kashenok.rentcar.command.navigation;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import javax.servlet.http.HttpServletRequest;

/**
 * The class RegistrationNeedCommand. Redirects user to registration page.
 */
public class RegistrationNeedCommand implements ICommand {

    public static final String REF_PAGE = "refPage";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        request.getSession().setAttribute(REF_PAGE, "page.registration");
        return ConfigurationManager.getInstance().getProperty("page.registration");
    }

}
