package com.kashenok.rentcar.command.user;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.entity.Car;
import com.kashenok.rentcar.entity.RentTermHolder;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.CarService;
import com.kashenok.rentcar.service.OrderService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * The class OrderNeedCreateCommand. Select order data to creation new order.
 */
public class OrderNeedCreateCommand implements ICommand {

    public static final String CAR = "car";
    public static final String CREATE_ORDER = "orderCreate";
    public static final String CAR_ID = "carId";
    public static final String ORDERED_DAYS = "orderedDatesList";

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
        List<RentTermHolder> orderedDatesList = new ArrayList<>();
        try {
            int carId = Integer.parseInt(request.getParameter(CAR_ID));
            Car car = carService.fingCarById(carId);
            orderedDatesList = orderService.findCarOrderedDates(carId);
            RefSessionCleaner.cleanAttributes(request);
            request.getSession().setAttribute(CAR, car);
            request.getSession().setAttribute(CREATE_ORDER, "orderCreate");
            request.getSession().setAttribute(ORDERED_DAYS, orderedDatesList);

        } catch (ServiceException ex) {
            throw new CommandException("Impossible to prepare execute command in OrderNeedCreateCommand ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }
}
