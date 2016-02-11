package com.kashenok.rentcar.command.admin;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.entity.OrderStatus;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.OrderService;
import com.kashenok.rentcar.exception.ServiceException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * The class AcceptFinishedOrderCommand. Sets status of order like 'finished'
 */
public class AcceptFinishedOrderCommand implements ICommand {

    public static final String ORDER_ID = "orderId";
    public static final String FINISHED_ORDERS = "finishedOrders";

    
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
            int orderId = Integer.parseInt(request.getParameter(ORDER_ID));
            boolean isDone = orderService.changeOrderStatusById(orderId, OrderStatus.FINISHED);
            if (isDone) {
                List<Order> orderFinishedList = orderService.findOrdersByStatus(OrderStatus.FINISHED);
                request.getSession().setAttribute(FINISHED_ORDERS, orderFinishedList);
            }

        } catch (ServiceException ex) {
            throw new CommandException("Impossible to prepare execute command in UserBlockCommand ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
