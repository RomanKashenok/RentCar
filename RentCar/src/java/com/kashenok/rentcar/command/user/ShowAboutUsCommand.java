package com.kashenok.rentcar.command.user;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.util.RefSessionCleaner;
import javax.servlet.http.HttpServletRequest;

/**
 * The class ShowAboutUsCommand. Redirects application user to page with 'about
 * us' information.
 */
public class ShowAboutUsCommand implements ICommand {

    public static final String ABOUT_TEXT = "aboutText";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     */
    @Override
    public String execute(HttpServletRequest request) {

        RefSessionCleaner.cleanAttributes(request);
        request.getSession().setAttribute(ABOUT_TEXT, "text.about");

        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
