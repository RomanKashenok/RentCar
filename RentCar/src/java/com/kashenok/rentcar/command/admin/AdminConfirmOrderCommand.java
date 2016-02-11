
package com.kashenok.rentcar.command.admin;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.entity.OrderStatus;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.OrderService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * The class AdminConfirmOrderCommand. Administrator confirm order from concrete clients orders list.
 */
public class AdminConfirmOrderCommand implements ICommand {

    public static final String ORDER_ID = "orderId";
    public static final String USER_CHANGE_ID = "userChangeId";
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
        int userChangeId = (int) request.getSession().getAttribute(USER_CHANGE_ID);
        int orderId = Integer.parseInt(request.getParameter(ORDER_ID));
        boolean isConfirmed = false;
        OrderService orderService = new OrderService();
        try {
            isConfirmed = orderService.changeOrderStatusById(orderId, OrderStatus.CONFIRMED);
            if (isConfirmed) {
                List<Order> userOrderList = new ArrayList<>();
                userOrderList = orderService.findAllUserOrders(userChangeId);
                RefSessionCleaner.cleanAttributes(request);
                request.getSession().setAttribute(USER_CHANGE_ORDERS_LIST, userOrderList);
                request.getSession().setAttribute(USER_CHANGE_ID, userChangeId);
                return ConfigurationManager.getInstance().getProperty("page.main");
            }
        } catch (ServiceException ex) {
            throw new CommandException("Impossible to get List of orders in AdminConfirmOrderCommand: ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
