package com.kashenok.rentcar.command.admin;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.entity.Car;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.CarService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import javax.servlet.http.HttpServletRequest;

/**
 * The class CarNeedChangeCommand. Redirects user to page with changing Car
 * fields form
 */
public class CarNeedChangeCommand implements ICommand {

    public static final String CAR_ID = "carId";
    public static final String CAR_CHANGE = "carChange";
    public static final String CAR_GET_ERROR = "carGetError";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        CarService carService = new CarService();
        try {
            long carId = Integer.parseInt(request.getParameter(CAR_ID));
            Car carChange = carService.fingCarById(carId);
            if (carChange != null) {
                RefSessionCleaner.cleanAttributes(request);
                request.setAttribute(CAR_CHANGE, carChange);
            } else {
                request.setAttribute(CAR_GET_ERROR, "car.get.error");
            }
        } catch (ServiceException ex) {
            throw new CommandException("Impossible process execute method in AdminPenaltyConfirmCommand class: ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
