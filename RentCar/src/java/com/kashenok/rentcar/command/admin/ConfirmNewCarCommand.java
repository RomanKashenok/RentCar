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
 * The class ConfirmNewCarCommand. Make and write changes in Car object fields
 */
public class ConfirmNewCarCommand implements ICommand {

    public static final String CAR_SAVED = "carSaved";
    public static final String CAR_SAVE_FAILED = "carSaveFailed";
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
        Map<String, String> parameters = new HashMap<>();
        CarService carService = new CarService();
        boolean isSaved = false;
        try {
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);
                for (int i = 0; i < paramValues.length; i++) {
                    String paramValue = paramValues[i];
                    parameters.put(paramName, paramValue);
                }
            }
            if (!carService.checkVIN(parameters.get(VIN))) {
                request.setAttribute(VIN_ALREADY_EXISTS, "vin.already.exists");
                return ConfigurationManager.getInstance().getProperty("page.main");
            }
            isSaved = carService.addNewCar(parameters);
            if (isSaved) {
                RefSessionCleaner.cleanAttributes(request);
                request.setAttribute(CAR_SAVED, "car.saved");
            } else {
                request.setAttribute(CAR_SAVE_FAILED, "car.save.failed");
            }
        } catch (ServiceException ex) {
            throw new CommandException("Impossible to add new car in CarChangeCommand: ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
