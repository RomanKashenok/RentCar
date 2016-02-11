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
 * The class AdminCancelOrderCommand. Administrator annul order from concrete
 * clients orders list.
 */
public class AdminCancelOrderCommand implements ICommand {

    public static final String ORDER_ID = "orderId";
    public static final String USER_CHANGE_ID = "userChangeId";
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
        int userChangeId = (int) request.getSession().getAttribute(USER_CHANGE_ID);
        int orderId = Integer.parseInt(request.getParameter(ORDER_ID));
        OrderService orderService = new OrderService();
        Order order = null;
        try {
            order = orderService.findOrderById(orderId);
            RefSessionCleaner.cleanAttributes(request);
            request.getSession().setAttribute(ORDER, order);
            request.getSession().setAttribute(ORDER_NEED_CANCEL, "order.canceled.reason");
            request.getSession().setAttribute(REF_PAGE, "page.main");
            request.getSession().setAttribute(USER_CHANGE_ID, userChangeId);
        } catch (ServiceException ex) {
            throw new CommandException("Impossible to prepare command in AdminConfirmOrderCommand: ", ex);
        }

//        
//        boolean isConfirmed = false;
//        OrderService orderService = new OrderService();
//        try {
//            isConfirmed = orderService.cancelUsersOrder(orderId);
//            if (isConfirmed) {
//                List<Order> userOrderList = new ArrayList<>();
//                userOrderList = orderService.findAllUserOrders(userChangeId);
//                RefSessionCleaner.cleanAttributes(request);
//                request.getSession().setAttribute(USER_CHANGE_ORDERS_LIST, userOrderList);
//                request.getSession().setAttribute(USER_CHANGE_ID, userChangeId);
//                return ConfigurationManager.getInstance().getProperty("page.main");
//            }
//        } catch (ServiceException ex) {
//            throw new CommandException("Impossible to prepare command in AdminCancelOrderCommand: ", ex);
//        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
