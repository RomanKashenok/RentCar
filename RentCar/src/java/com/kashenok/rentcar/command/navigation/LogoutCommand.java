package com.kashenok.rentcar.command.navigation;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.resource.ConfigurationManager;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 * The class LogoutCommand. Remove users information from session.
 */
public class LogoutCommand implements ICommand {

    static final Logger LOG = Logger.getLogger(LogoutCommand.class);

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getInstance().getProperty("page.index");
        request.getSession().invalidate();
        LOG.info("LogoutCommand executed correctly");
        return page;
    }

}
