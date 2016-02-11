package com.kashenok.rentcar.command.user;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.OrderService;
import com.kashenok.rentcar.service.UserService;
import com.kashenok.rentcar.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;

/**
 * The class OrderConfirmCommand. Confirm and set created order
 */
public class OrderConfirmCommand implements ICommand {

    public static final String ORDER = "order";
    public static final String USER_ID = "userId";
    public static final String USER_BALANCE = "userBalance";
    public static final String ORDER_CONFIRMED = "orderConfirmed";
    public static final String CONFIRM_ORDER_FAILED = "confirmOrderFailedMessage";
    public static final String NEED_MORE_MONEY = "balanceDeficit";

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
        boolean isBalanceUpdated = false;
        try {
            int userId = (int) request.getSession().getAttribute(USER_ID);
            Order order = (Order) request.getSession().getAttribute(ORDER);
            double userBalance = (double) request.getSession().getAttribute(USER_BALANCE);
            double orderCoast = order.getCoast();
            if (orderCoast > userBalance) {
                request.setAttribute(NEED_MORE_MONEY, "user.balance.daficit");
            } else {
                OrderService orderService = new OrderService();
                UserService userService = new UserService();
                isConfirmed = orderService.confirmOrder(order, userId);
                if (isConfirmed) {
                    request.getSession().removeAttribute(ORDER);
                    request.setAttribute(ORDER_CONFIRMED, "order.confirmed");
                    isBalanceUpdated = userService.updateBalance(userId, userBalance, order.getCoast());
                    if (isBalanceUpdated) {
                        userBalance = userService.findUserRefilledBalance(userId, userBalance);
                        request.getSession().setAttribute(USER_BALANCE, userBalance);
                    }
                } else {
                    request.setAttribute(CONFIRM_ORDER_FAILED, "order.confirm.failed");
                }
            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
