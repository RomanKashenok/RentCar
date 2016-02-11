package com.kashenok.rentcar.command.navigation;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.entity.Car;
import com.kashenok.rentcar.entity.CarStatus;
import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.entity.OrderStatus;
import com.kashenok.rentcar.entity.Penalty;
import com.kashenok.rentcar.entity.User;
import com.kashenok.rentcar.entity.UserRole;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.CarService;
import com.kashenok.rentcar.service.OrderService;
import com.kashenok.rentcar.service.PenaltyService;
import com.kashenok.rentcar.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * The class LoginCommand. Add user information to the session.
 */
public class LoginCommand implements ICommand {

    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String ROLE = "role";
    public static final String INCORRECT_LOGIN_OR_PASSWORD = "errorLoginPassMessage";
    public static final String USER_ID = "userId";
    public static final String USER_BALANCE = "userBalance";
    public static final String EMAIL = "email";
    public static final String PASSPORT_NUMBER = "passportNumber";
    public static final String FIRST_NAME = "firstName";
    public static final String REF_PAGE = "refPage";
    public static final String CARS_LIST = "carsList";
    public static final String USERS_LIST = "usersList";
    public static final String WELCOME_TEXT = "welcomeText";
    public static final String USER_BLOCKED = "userBlocked";
    public static final String ORDER_UNCONFIRMED = "unconfirmed";
    public static final String UNCONFIRMED_ORDERS = "unconfirmedOrders";
    public static final String FINISHED_ORDERS = "finishedOrders";
    public static final String PENALTY = "penalty";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String page = null;
        UserService userService = new UserService();
        CarService carService = new CarService();
        PenaltyService penaltyService = new PenaltyService();
        OrderService orderService = new OrderService();
        try {
            User user = userService.checkUser(login, password);
            UserRole role = user.getRole();
            if (role != null) {
                switch (role) {
                    case USER:
                        request.getSession().setAttribute(USER_BALANCE, user.getBalance());
                        request.getSession().setAttribute(PASSPORT_NUMBER, user.getPassportNumber());
                        request.getSession().setAttribute(EMAIL, user.getEmail());
                        List<Car> carsList = carService.fingAvailibleCars(CarStatus.AVAILABLE);
                        Penalty penalty = penaltyService.findUserPenalty(user.getUserId(), false);
                        request.getSession().setAttribute(CARS_LIST, carsList);
                        if (penalty.getPenaltyId() != 0) {
                            request.getSession().setAttribute(PENALTY, penalty);
                        }
                        page = ConfigurationManager.getInstance().getProperty("page.main");
                        break;
                    case ADMIN:
                        List<User> usersList = userService.findActiveUsers();
                        List<Order> ordersList = orderService.findOrdersByStatus(OrderStatus.valueOf(ORDER_UNCONFIRMED.toUpperCase()));
                        List<Order> orderFinishedList = orderService.findOrdersByStatus(OrderStatus.FINISHED);
                        request.getSession().setAttribute(UNCONFIRMED_ORDERS, ordersList);
                        request.getSession().setAttribute(FINISHED_ORDERS, orderFinishedList);
                        request.getSession().setAttribute(USERS_LIST, usersList);
                        request.getSession().setAttribute(WELCOME_TEXT, "text.main.page");
                        page = ConfigurationManager.getInstance().getProperty("page.main");
                        break;
                    case DISABLED:
                        request.setAttribute(USER_BLOCKED, "user.blocked");
                        page = ConfigurationManager.getInstance().getProperty("page.login");
                        break;
                    default:
                        request.setAttribute(WELCOME_TEXT, "welcome.text");
                        page = ConfigurationManager.getInstance().getProperty("page.login");
                        break;
                }
                request.getSession().setAttribute(USER_ID, user.getUserId());
                request.getSession().setAttribute(FIRST_NAME, user.getFirstName());
                request.getSession().setAttribute(ROLE, role.toString());
                request.getSession().setAttribute(WELCOME_TEXT, "text.main.page");
            } else {
                request.setAttribute(INCORRECT_LOGIN_OR_PASSWORD, "login.incorrect");
                page = ConfigurationManager.getInstance().getProperty("page.login");
            }

        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
        request.getSession().setAttribute(REF_PAGE, "page.main");
        return page;
    }

}
