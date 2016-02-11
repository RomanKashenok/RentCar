package com.kashenok.rentcar.command.admin;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.entity.Car;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.CarService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * The class AdminShowCarsCommand. Get all car entries.
 */
public class AdminShowCarsCommand implements ICommand {

    public static final String ADMIN_CARS_LIST = "adminCarsList";

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
            List<Car> adminCarsList = carService.fingAllCars();
            RefSessionCleaner.cleanAttributes(request);
            request.getSession().setAttribute(ADMIN_CARS_LIST, adminCarsList);
        } catch (ServiceException ex) {
            throw new CommandException("Impossible process execute method in AdminPenaltyConfirmCommand class: ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
