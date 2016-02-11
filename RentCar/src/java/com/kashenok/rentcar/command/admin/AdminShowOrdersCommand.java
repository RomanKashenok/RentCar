package com.kashenok.rentcar.command.admin;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.OrderService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * The class AdminShowOrdersCommand. Get all Order entries.
 */
public class AdminShowOrdersCommand implements ICommand {

    public static final String ALL_ORDERS_LIST = "allOrders";
    public static final String ALL_ORDERS_LIST_EMPTY = "allOrdersEmpty";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        List<Order> allOrdersList = new ArrayList<>();
        OrderService orderService = new OrderService();

        try {
            allOrdersList = orderService.findAllOrder();
            if (allOrdersList != null && !allOrdersList.isEmpty()) {
                RefSessionCleaner.cleanAttributes(request);
                request.getSession().setAttribute(ALL_ORDERS_LIST, allOrdersList);
            } else {
                RefSessionCleaner.cleanAttributes(request);
                request.setAttribute(ALL_ORDERS_LIST_EMPTY, "order.list.empty");
            }
        } catch (ServiceException ex) {
            throw new CommandException("Impossible process execute method in AdminShowOrdersCommand class: ", ex);
        }

        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
