package com.kashenok.rentcar.command.admin;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.entity.OrderStatus;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.OrderService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * The class AdminShowOrdersByStatusCommand. Get all Order entries with selected
 * OrderStatus.
 */
public class AdminShowOrdersByStatusCommand implements ICommand {

    public static final String ADMIN_STAUS_ORDER_LIST = "adminStatusOrderList";
    public static final String ORDERS_GET_ERROR = "ordersGetError";
    public static final String ORDER_STATUS = "orderStatus";

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
        OrderStatus orderStatus = OrderStatus.valueOf(request.getParameter(ORDER_STATUS).toUpperCase());
        try {
            List<Order> statusOrderList = orderService.findOrdersByStatus(orderStatus);
            if (statusOrderList.isEmpty() || statusOrderList == null) {
                request.setAttribute(ORDERS_GET_ERROR, "orders.list.empty");
                return ConfigurationManager.getInstance().getProperty("page.main");
            }
            RefSessionCleaner.cleanAttributes(request);
            request.getSession().setAttribute(ADMIN_STAUS_ORDER_LIST, statusOrderList);
        } catch (ServiceException ex) {
            throw new CommandException("Impossible process execute method in AdminPenaltyConfirmCommand class: ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
