package com.kashenok.rentcar.command.admin;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.entity.User;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.UserService;
import com.kashenok.rentcar.exception.ServiceException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * The class UserBlockCommand. Change concrete user role to 'disabled'.
 */
public class UserBlockCommand implements ICommand {

    public static final String USER_ID = "userId";
    public static final String ACTION = "action";
    public static final String USERS_LIST = "usersList";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        int userId = Integer.parseInt(request.getParameter(USER_ID));
        String action = request.getParameter(ACTION);
        UserService userService = new UserService();
        try {
            boolean isDone = userService.blockUnblockUser(userId, action);
            if (isDone) {
                List<User> usersList = userService.findActiveUsers();
                request.getSession().setAttribute(USERS_LIST, usersList);
            }

        } catch (ServiceException ex) {
            throw new CommandException("Impossible to prepare execute command in UserBlockCommand ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
