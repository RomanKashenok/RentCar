package com.kashenok.rentcar.command.admin;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.OrderService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import javax.servlet.http.HttpServletRequest;

/**
 * The class AdminPenaltySetCommand. Redirects user to creating new Penalty
 * object form.
 */
public class AdminPenaltySetCommand implements ICommand {

    public static final String ORDER_ID = "orderId";
    public static final String USER_ID = "userId";
    public static final String SET_PENALTY = "setPenaltyToUser";
    public static final String ORDER = "orderPenalty";
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
        Order order = null;
        OrderService orderService = new OrderService();
        try {
            int orderId = Integer.parseInt(request.getParameter(ORDER_ID));
            order = orderService.findOrderById(orderId);
            RefSessionCleaner.cleanAttributes(request);
            request.getSession().setAttribute(ORDER, order);
            request.getSession().setAttribute(SET_PENALTY, "penalty.set");
            request.getSession().setAttribute(REF_PAGE, "page.main");

        } catch (ServiceException ex) {
            throw new CommandException("Impossible process execute method in AdminPenaltySetCommand class: ", ex);
        }

        return ConfigurationManager.getInstance().getProperty("page.main");
    }
}
