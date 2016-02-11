package com.kashenok.rentcar.command.navigation;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.entity.Car;
import com.kashenok.rentcar.entity.CarStatus;
import com.kashenok.rentcar.entity.User;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.CarService;
import com.kashenok.rentcar.service.UserService;
import com.kashenok.rentcar.exception.ServiceException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * The class RegistrationCommand. Create new User object and set new user
 * data to session.
 */
public class RegistrationCommand implements ICommand {

    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String PASSPORT_NUMBER = "passportNumber";
    public static final String USER_BALANCE = "userBalance";
    public static final String USER_ID = "userId";
    public static final String ROLE = "role";
    public static final String CARS_LIST = "carsList";
    public static final String REF_PAGE = "refPage";
    public static final String WELCOME_TEXT = "welcomeText";

    public static final String REGISTRATION_ERROR = "errorRegistration";
    public static final String LOGIN_ALREADY_EXISTS = "loginAlreadyExists";
    public static final String EMAIL_ALREADY_EXISTS = "emailAlreadyExists";
    public static final String PASSPORT_ALREADY_EXISTS = "passportAlreadyExists";

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
        CarService carService = new CarService();

        try {
            String login = request.getParameter(LOGIN);
            String password = request.getParameter(PASSWORD);
            String email = request.getParameter(EMAIL);
            String firstName = request.getParameter(FIRST_NAME);
            String lastName = request.getParameter(LAST_NAME);
            String passportNumber = request.getParameter(PASSPORT_NUMBER);

            request.setAttribute(LOGIN, login);
            request.setAttribute(EMAIL, email);
            request.setAttribute(FIRST_NAME, firstName);
            request.setAttribute(LAST_NAME, lastName);
            request.setAttribute(PASSPORT_NUMBER, passportNumber);
            if (!userService.checkLogin(login)) {
                request.setAttribute(LOGIN_ALREADY_EXISTS, "loginAlreadyExists");
                return ConfigurationManager.getInstance().getProperty("page.registration");
            }
            if (!userService.checkEmail(email)) {
                request.setAttribute(EMAIL_ALREADY_EXISTS, "emailAlreadyExists");
                return ConfigurationManager.getInstance().getProperty("page.registration");
            }
            if (!userService.checkPassport(passportNumber)) {
                request.setAttribute(PASSPORT_ALREADY_EXISTS, "passportAlreadyExists");
                return ConfigurationManager.getInstance().getProperty("page.registration");
            }
            boolean isAdded = userService.addUser(login, password, email, firstName, lastName, passportNumber);
            if (!isAdded) {
                request.setAttribute(REGISTRATION_ERROR, "registrationError");
                return ConfigurationManager.getInstance().getProperty("page.registration");
            }
            User user = userService.checkUser(login, password);
            request.getSession().setAttribute(ROLE, user.getRole().toString());
            request.getSession().setAttribute(USER_ID, user.getUserId());
            request.getSession().setAttribute(FIRST_NAME, user.getFirstName());
            request.getSession().setAttribute(USER_BALANCE, user.getBalance());
            request.getSession().setAttribute(EMAIL, user.getEmail());
            request.getSession().setAttribute(PASSPORT_NUMBER, user.getPassportNumber());
            List<Car> carsList = carService.fingAvailibleCars(CarStatus.AVAILABLE);
            request.getSession().setAttribute(CARS_LIST, carsList);
            request.getSession().setAttribute(WELCOME_TEXT, "text.main.page");
            request.getSession().setAttribute(REF_PAGE, "page.main");

        } catch (ServiceException ex) {

            throw new CommandException(ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
