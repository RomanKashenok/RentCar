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
 * The class OrderChangeConfirmCommand. Confirm and set changed data to selected
 * order.
 */
public class OrderChangeConfirmCommand implements ICommand {

    public static final String ORDER_EDITED = "orderEdited";
    public static final String USER_ID = "userId";
    public static final String USER_BALANCE = "userBalance";
    public static final String ORDER_CONFIRMED = "orderConfirmed";
    public static final String CONFIRM_ORDER_FAILED = "confirmOrderFailedMessage";
    public static final String BALANCE_DIFFERENCE = "balanceDiference";
    public static final String ORDER_EDIT_COAST = "orderEditCoast";
    public static final String ORDER_EDIT = "orderEdit";

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
        boolean isUpdated = false;
        boolean isBalanceUpdated = false;
        try {
            int userId = (int) request.getSession().getAttribute(USER_ID);
            Order orderEdited = (Order) request.getSession().getAttribute(ORDER_EDITED);
            double userBalance = (double) request.getSession().getAttribute(USER_BALANCE);
            double orderEditedCoast = orderEdited.getCoast();
            double oldOrderCoast = (double) request.getSession().getAttribute(ORDER_EDIT_COAST);
            double diffCoast = orderEditedCoast - oldOrderCoast;
            
            if (diffCoast > userBalance) {
                request.setAttribute(NEED_MORE_MONEY, "user.balance.daficit");
            } else {
                OrderService orderService = new OrderService();
                UserService userService = new UserService();
                isUpdated = orderService.updateOrder(orderEdited);
                if (isUpdated) {
                    RefSessionCleaner.cleanAttributes(request);
                    request.setAttribute(ORDER_CONFIRMED, "order.confirmed");
                    isBalanceUpdated = userService.updateBalance(userId, userBalance, diffCoast);
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
