package com.kashenok.rentcar.command.user;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.entity.Car;
import com.kashenok.rentcar.entity.CarStatus;
import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.CarService;
import com.kashenok.rentcar.service.OrderService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * The class OrderNeedChangeCommand. Select order to edition.
 */
public class OrderNeedChangeCommand implements ICommand {

    public static final String ORDER_EDIT = "orderEdit";
    public static final String CARS_LIST = "carsList";
    public static final String ORDER_ID = "orderId";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String page = null;
        OrderService orderService = new OrderService();
        CarService carService = new CarService();

        try {
            int orderId = Integer.parseInt(request.getParameter(ORDER_ID));
            Order order = orderService.findOrderById(orderId);
            List<Car> carsList = carService.fingAvailibleCars(CarStatus.AVAILABLE);
            RefSessionCleaner.cleanAttributes(request);
            request.getSession().setAttribute(ORDER_EDIT, order);
            request.setAttribute(CARS_LIST, carsList);
        } catch (ServiceException ex) {
            throw new CommandException("Impossible to prepare execute command in OrderChangeCommand ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
