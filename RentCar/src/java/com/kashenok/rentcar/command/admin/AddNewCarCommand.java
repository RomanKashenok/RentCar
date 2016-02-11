package com.kashenok.rentcar.command.admin;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.util.RefSessionCleaner;
import javax.servlet.http.HttpServletRequest;

/**
 * The class AddNewCarCommand. Redirects user to creating new Car object page
 */
public class AddNewCarCommand implements ICommand {

    public static final String ADD_NEW_CAR = "addNewCar";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        RefSessionCleaner.cleanAttributes(request);
        request.getSession().setAttribute(ADD_NEW_CAR, "car.add.new");

        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
