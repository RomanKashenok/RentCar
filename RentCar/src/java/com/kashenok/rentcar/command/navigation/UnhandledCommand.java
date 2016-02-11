package com.kashenok.rentcar.command.navigation;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.resource.ConfigurationManager;
import javax.servlet.http.HttpServletRequest;

/**
 * The class UnhandledCommand performs if request.getParameter("page") == null.
 */
public class UnhandledCommand implements ICommand {

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     */
    @Override
    public String execute(HttpServletRequest request) {
        return ConfigurationManager.getInstance().getProperty("page.error");
    }

}
