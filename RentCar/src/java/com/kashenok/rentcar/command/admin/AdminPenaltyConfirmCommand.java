package com.kashenok.rentcar.command.admin;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.entity.OrderStatus;
import com.kashenok.rentcar.entity.Penalty;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.CarService;
import com.kashenok.rentcar.service.OrderService;
import com.kashenok.rentcar.service.PenaltyService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * The class AdminPenaltyConfirmCommand. Adding new entry of Penalty object
 */
public class AdminPenaltyConfirmCommand implements ICommand {

    public static final String ORDER_ID = "orderId";
    public static final String USER_ID = "userId";
    public static final String CAR_ID = "carId";
    public static final String PENALTY_SUM = "penaltySum";
    public static final String PENALTY_MESSAGE = "penaltyMessage";
    public static final String FAILED_PENALTY_MAKING = "penaltyMakingFailed";
    public static final String PENALTY_ADDED = "penaltyAdded";
    public static final String FINISHED_ORDERS = "finishedOrders";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        PenaltyService penaltyService = new PenaltyService();
        CarService carService = new CarService();
        OrderService orderService = new OrderService();
        Penalty penalty = new Penalty();

        try {
            int carId = Integer.parseInt(request.getParameter(CAR_ID));
            int orderId = Integer.parseInt(request.getParameter(ORDER_ID));

            penalty.setMessage(request.getParameter(PENALTY_MESSAGE));
            penalty.setOrderId(orderId);
            penalty.setCarId(carId);
            penalty.setSum(Double.parseDouble(request.getParameter(PENALTY_SUM)));
            penalty.setUserId(Integer.parseInt(request.getParameter(USER_ID)));
            if (penaltyService.addNewPenalty(penalty)) {
                RefSessionCleaner.cleanAttributes(request);
                List<Order> orderFinishedList = orderService.findOrdersByStatus(OrderStatus.FINISHED);
                request.getSession().setAttribute(FINISHED_ORDERS, orderFinishedList);
                request.setAttribute(PENALTY_ADDED, "penalty.added");
            } else {
                request.setAttribute(FAILED_PENALTY_MAKING, "penalty.make.failed");
            }

        } catch (ServiceException ex) {
            throw new CommandException("Impossible process execute method in AdminPenaltyConfirmCommand class: ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
