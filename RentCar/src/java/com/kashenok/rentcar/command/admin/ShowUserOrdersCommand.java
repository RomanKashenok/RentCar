package com.kashenok.rentcar.command.admin;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.OrderService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * The class ShowUserOrdersCommand. Get all orders of concrete user.
 */
public class ShowUserOrdersCommand implements ICommand {

    public static final String USER_CHANGE_ID = "userChangeId";
    public static final String NO_ORDERS = "ordersAbsent";
    public static final String USER_CHANGE_ORDERS_LIST = "userChangeOrdersList";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        int userChangeId = Integer.parseInt(request.getParameter(USER_CHANGE_ID));
        OrderService orderSevice = new OrderService();
        List<Order> userOrderList = new ArrayList<Order>();

        try {
            userOrderList = orderSevice.findAllUserOrders(userChangeId);
            if (userOrderList.size() > 0) {
                RefSessionCleaner.cleanAttributes(request);
                request.getSession().setAttribute(USER_CHANGE_ORDERS_LIST, userOrderList);
                request.getSession().setAttribute(USER_CHANGE_ID, userChangeId);
                return ConfigurationManager.getInstance().getProperty("page.main");
            } else {
                RefSessionCleaner.cleanAttributes(request);
                request.setAttribute(NO_ORDERS, "orders.absent");
                return ConfigurationManager.getInstance().getProperty("page.main");
            }
        } catch (ServiceException ex) {
            throw new CommandException("Impossible to prepare execute command in ShowUserProfileCommand ", ex);
        }
    }

}
