package com.kashenok.rentcar.command.user;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.entity.User;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.UserService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import javax.servlet.http.HttpServletRequest;

/**
 * The class UserProfileChangeCommand. Creates User @class object and set it to
 * session for further changing.
 */
public class UserProfileChangeCommand implements ICommand {

    public static final String USER_ID = "userId";
    public static final String USER_CHANGE = "userChange";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        UserService userService = new UserService();
        User userChange = new User();

        try {
            int userId = (int) request.getSession().getAttribute(USER_ID);
            userChange = userService.findUserById(userId);
            RefSessionCleaner.cleanAttributes(request);
            request.getSession().setAttribute(USER_CHANGE, userChange);
        } catch (ServiceException ex) {
            throw new CommandException("Impossible to get List of orders in UserProfileChangeCommand: ", ex);
        }

        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
