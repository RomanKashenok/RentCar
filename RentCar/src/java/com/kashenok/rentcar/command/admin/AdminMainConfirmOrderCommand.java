package com.kashenok.rentcar.command.admin;

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
 * The class AdminMainConfirmOrderCommand. Change order status to 'confirmed'.
 * Administrator confirms clients order from all unconfirmed orders list.
 */
public class AdminMainConfirmOrderCommand implements ICommand {

    public static final String ORDER_ID = "orderId";
    public static final String ORDER_UNCONFIRMED = "unconfirmed";
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
        boolean isConfirmed = false;
        OrderService orderService = new OrderService();
        try {
            int orderId = Integer.parseInt(request.getParameter(ORDER_ID));
            isConfirmed = orderService.changeOrderStatusById(orderId, OrderStatus.CONFIRMED);
            if (isConfirmed) {
                List<Order> ordersList = orderService.findOrdersByStatus(OrderStatus.valueOf(ORDER_UNCONFIRMED.toUpperCase()));

                RefSessionCleaner.cleanAttributes(request);
                request.getSession().setAttribute(UNCONFIRMED_ORDERS, ordersList);
                return ConfigurationManager.getInstance().getProperty("page.main");
            }
        } catch (ServiceException ex) {
            throw new CommandException("Impossible to get List of orders in AdminConfirmOrderCommand: ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }
}
