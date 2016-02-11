package com.kashenok.rentcar.command.navigation;

import com.kashenok.rentcar.command.ICommand;
import static com.kashenok.rentcar.command.navigation.LoginCommand.ORDER_UNCONFIRMED;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.entity.OrderStatus;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.OrderService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * The class GoToMainCommand. Returns application user to the main page.
 */
public class GoToMainCommand implements ICommand {

    public static final String WELCOME_TEXT = "welcomeText";
    public static final String UNCONFIRMED_ORDERS = "unconfirmedOrders";

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
            RefSessionCleaner.cleanAttributes(request);
            OrderService orderService = new OrderService();
            List<Order> ordersList = orderService.findOrdersByStatus(OrderStatus.valueOf(ORDER_UNCONFIRMED.toUpperCase()));
            request.getSession().setAttribute(UNCONFIRMED_ORDERS, ordersList);
            request.getSession().setAttribute(WELCOME_TEXT, "text.main.page");
        } catch (ServiceException ex) {
            Logger.getLogger(GoToMainCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");

    }

}
