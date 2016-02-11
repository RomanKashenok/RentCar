package com.kashenok.rentcar.command.user;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.OrderService;
import com.kashenok.rentcar.service.UserService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import javax.servlet.http.HttpServletRequest;

/**
 * The class OrderDeleteConfirmCommand. Confirm and removes selected order.
 */
public class OrderDeleteConfirmCommand implements ICommand {

    public static final String USER_ID = "userId";
    public static final String USER_BALANCE = "userBalance";

    public static final String ORDER_ID = "orderId";
    public static final String ORDER_DELETE = "orderDelete";
    public static final String ORDER_DELETED = "orderDeleted";
    public static final String ORDER_DELETE_FAILED = "orderDeleteFailed";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        boolean isDeleted = false;
        boolean isBalanceUpdated = false;
        OrderService orderService = new OrderService();
        UserService userService = new UserService();
        try {
            int userId = (int) request.getSession().getAttribute(USER_ID);
            double userBalance = (double) request.getSession().getAttribute(USER_BALANCE);
            Order order = (Order) request.getSession().getAttribute(ORDER_DELETE);
            if (order != null) {
                long orderId = order.getOrderId();
                double returnCoast = order.getCoast();

                isDeleted = orderService.deleteOrderById(orderId);
                if (isDeleted) {
                    isBalanceUpdated = userService.refillBalance(userBalance, returnCoast, userId);
                    if (isBalanceUpdated) {
                        userBalance = userService.findUserRefilledBalance(userId, userBalance);
                        request.getSession().setAttribute(USER_BALANCE, userBalance);
                    }
                    RefSessionCleaner.cleanAttributes(request);
                    request.getSession().setAttribute(ORDER_DELETED, "order.deleted");
                } else {
                    request.setAttribute(ORDER_DELETE_FAILED, "order.delte.failed");
                }
            }
        } catch (ServiceException ex) {
            throw new CommandException("Impossible to prepare execute command in OrderDeleteConfirmCommand ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
