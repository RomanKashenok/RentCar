package com.kashenok.rentcar.command.admin;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.CarService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * The class CarChangeCommand. Make and write changes to Car fields.
 */
public class CarChangeCommand implements ICommand {

    public static final String CAR_ID = "carId";
    public static final String CAR_UPDATED = "carUpdated";
    public static final String CAR_UPDATE_FAILED = "carUpdateFailed";
    public static final String VIN = "vin";
    public static final String VIN_ALREADY_EXISTS = "vinAlreadyExists";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        Map<String, String> parameters = new HashMap<String, String>();
        CarService carService = new CarService();
        boolean isUpdated = false;
        try {
            long carId = Long.parseLong(request.getParameter(CAR_ID));
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);
                for (int i = 0; i < paramValues.length; i++) {
                    String paramValue = paramValues[i];
                    parameters.put(paramName, paramValue);
                }
            }
            isUpdated = carService.updateCar(parameters, carId);
            if (isUpdated) {
                RefSessionCleaner.cleanAttributes(request);
                request.setAttribute(CAR_UPDATED, "car.updated");
            } else {
                request.setAttribute(CAR_UPDATE_FAILED, "car.update.failed");
            }
        } catch (ServiceException ex) {
            throw new CommandException("Impossible to update car in CarChangeCommand: ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
