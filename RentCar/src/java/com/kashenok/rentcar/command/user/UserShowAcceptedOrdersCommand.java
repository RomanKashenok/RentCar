package com.kashenok.rentcar.command.user;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.entity.OrderStatus;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.OrderService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * The class UserShowAcceptedOrdersCommand. Look for accepted orders of current
 * user and set list of them to session.
 */
public class UserShowAcceptedOrdersCommand implements ICommand {

    public static final String USER_ID = "userId";
    public static final String ACCEPTED_ORDERS_LIST = "acceptedOrderList";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        OrderService orderService = new OrderService();
        try {
            List<Order> acceptedOrderList = orderService.findOrdersByUserIdAndStatus((int) request.getSession().getAttribute(USER_ID), OrderStatus.CONFIRMED);
            RefSessionCleaner.cleanAttributes(request);
            request.getSession().setAttribute(ACCEPTED_ORDERS_LIST, acceptedOrderList);
        } catch (ServiceException ex) {
            throw new CommandException("Impossible to get List of orders in ShowUserOrderListCommand: ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }
}
