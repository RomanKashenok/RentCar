/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kashenok.rentcar.command.admin;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.OrderService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import javax.servlet.http.HttpServletRequest;

/**
 * The class AdminMainCancelOrderCommand. Redirects user to page with cancel
 * order message input form
 */
public class AdminMainCancelOrderCommand implements ICommand {

    public static final String ORDER_ID = "orderId";
    public static final String ORDER_UNCONFIRMED = "unconfirmed";
    public static final String UNCONFIRMED_ORDERS = "unconfirmedOrders";
    public static final String ORDER_NEED_CANCEL = "orderCancelReason";
    public static final String ORDER = "order";
    public static final String REF_PAGE = "refPage";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        int orderId = Integer.parseInt(request.getParameter(ORDER_ID));
        Order order = null;
        OrderService orderService = new OrderService();
        try {
            order = orderService.findOrderById(orderId);
            RefSessionCleaner.cleanAttributes(request);
            request.getSession().setAttribute(ORDER, order);
            request.getSession().setAttribute(ORDER_NEED_CANCEL, "order.canceled.reason");
            request.getSession().setAttribute(REF_PAGE, "page.main");

        } catch (ServiceException ex) {
            throw new CommandException("Impossible process execute method in AdminMainCancelOrderCommand class: ", ex);
        }

        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
