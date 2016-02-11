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
 * The class ConfirmOrderCancelCommand. Discard changes or creation Order object
 */
public class ConfirmOrderCancelCommand implements ICommand {

    public static final String ORDER_ID = "orderId";
    public static final String USER_CHANGE_ID = "userChangeId";
    public static final String USER_CHANGE_ORDERS_LIST = "userChangeOrdersList";
    public static final String ORDER_NEED_CANCEL = "orderCancelReason";
    public static final String CANCEL_MESSAGE = "cancelMessage";
    public static final String CANCELING_ERROR = "orderCancellingError";
    public static final String CANCELING_SUCCESSFULLY = "orderCancellingSuccessfully";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        int userChangeId = 0;
        if (request.getSession().getAttribute(USER_CHANGE_ID) != null) {
            userChangeId = (int) request.getSession().getAttribute(USER_CHANGE_ID);
        }
        int orderId = Integer.parseInt(request.getParameter(ORDER_ID));
        String cancelMessage = request.getParameter(CANCEL_MESSAGE);

        boolean isConfirmed = false;
        OrderService orderService = new OrderService();
        try {
            isConfirmed = orderService.cancelUsersOrder(orderId, cancelMessage);
            if (isConfirmed) {
                RefSessionCleaner.cleanAttributes(request);
                if (userChangeId != 0) {
                    List<Order> userOrderList = new ArrayList<>();
                    userOrderList = orderService.findAllUserOrders(userChangeId);
                    request.getSession().removeAttribute(ORDER_ID);
                    request.getSession().removeAttribute(ORDER_NEED_CANCEL);
                    request.getSession().setAttribute(USER_CHANGE_ORDERS_LIST, userOrderList);
                    request.getSession().setAttribute(USER_CHANGE_ID, userChangeId);
                }
                request.setAttribute(CANCELING_SUCCESSFULLY, "order.cancelling.error");
                return ConfigurationManager.getInstance().getProperty("page.main");
            } else {
                request.setAttribute(CANCELING_ERROR, "order.cancelling.error");
            }
        } catch (ServiceException ex) {
            throw new CommandException("Impossible to prepare command in ConfirmOrderCancelCommand: ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
