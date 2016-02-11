package com.kashenok.rentcar.command.user;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.OrderService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import javax.servlet.http.HttpServletRequest;

/**
 * The class OrderNeedDeleteCommand. Select order to delete.
 */
public class OrderNeedDeleteCommand implements ICommand {

    public static final String ORDER_ID = "orderId";
    public static final String ORDER_DELETE = "orderDelete";

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
            int orderId = Integer.parseInt(request.getParameter(ORDER_ID));
            OrderService orderService = new OrderService();
            Order order = orderService.findOrderById(orderId);
            order.setOrderId(orderId);
            RefSessionCleaner.cleanAttributes(request);
            request.getSession().setAttribute(ORDER_DELETE, order);

        } catch (ServiceException ex) {
            throw new CommandException("Impossible to prepare execute command in OrderNeedDeleteCommand ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
