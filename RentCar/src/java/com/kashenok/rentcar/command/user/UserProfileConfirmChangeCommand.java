package com.kashenok.rentcar.command.user;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.UserService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import javax.servlet.http.HttpServletRequest;

/**
 * The class UserProfileConfirmChangeCommand. Set and write new user data.
 */
public class UserProfileConfirmChangeCommand implements ICommand {

    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";

    public static final String UPDATE_ERROR = "errorUpdating";
    public static final String EMAIL_ALREADY_EXISTS = "emailAlreadyExists";
    public static final String PASSPORT_ALREADY_EXISTS = "passportAlreadyExists";
    public static final String PROFILE_CHANGED = "profileChanged";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            int userId = (int) request.getSession().getAttribute(USER_ID);
            String firstName = request.getParameter(FIRST_NAME);
            String lastName = request.getParameter(LAST_NAME);
            String email = request.getParameter(EMAIL);
            UserService userService = new UserService();

            request.setAttribute(FIRST_NAME, firstName);
            request.setAttribute(LAST_NAME, lastName);
            request.setAttribute(EMAIL, email);

            boolean isUpdated = userService.updateUser(firstName, lastName, email, userId);
            if (!isUpdated) {
                request.setAttribute(UPDATE_ERROR, "errorUpdating");
                return ConfigurationManager.getInstance().getProperty("page.main");
            }
            RefSessionCleaner.cleanAttributes(request);
            request.getSession().setAttribute(FIRST_NAME, firstName);
            request.getSession().setAttribute(LAST_NAME, lastName);
            request.getSession().setAttribute(EMAIL, email);
            request.setAttribute(PROFILE_CHANGED, "profile.changed");

        } catch (ServiceException ex) {
            throw new CommandException("Impossible to get List of orders in UserProfileConfirmChangeCommand: ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
