package com.kashenok.rentcar.command.navigation;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.resource.ConfigurationManager;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

/**
 * The class LanguageChangeCommand. Change locale of application and set it to session.
 */
public class LanguageChangeCommand implements ICommand {

    public static final String REQUEST_PARAM_LANGUAGE = "language";
    public static final String SESSION_ATTRIBUTE_LANG = "lang";
    public static final String RUSSIAN = "ru";
    public static final String ENGLISH = "en";

    public static final String REF_PAGE = "refPage";

   /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     */
    @Override
    public String execute(HttpServletRequest request) {
        String language = request.getParameter(REQUEST_PARAM_LANGUAGE);
        

        switch (language) {
            case RUSSIAN:
                request.getSession().setAttribute(SESSION_ATTRIBUTE_LANG, RUSSIAN);
                Locale.setDefault(new Locale(RUSSIAN));
                break;
            case ENGLISH:
                request.getSession().setAttribute(SESSION_ATTRIBUTE_LANG, ENGLISH);
                Locale.setDefault(new Locale(ENGLISH));
                break;
        }
        if (request.getSession().getAttribute(REF_PAGE) != null) {
            return ConfigurationManager.getInstance().getProperty((String) request.getSession().getAttribute(REF_PAGE));
        } else {
            return ConfigurationManager.getInstance().getProperty("page.login");
        }
    }
}
