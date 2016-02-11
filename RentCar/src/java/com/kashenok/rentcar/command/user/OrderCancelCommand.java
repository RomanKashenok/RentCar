package com.kashenok.rentcar.command.user;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.util.RefSessionCleaner;
import javax.servlet.http.HttpServletRequest;

/**
 * The class OrderCancelCommand. Cancels operation with users order.
 */
public class OrderCancelCommand implements ICommand {

    public static final String ORDER_CANCELED = "orderCanceled";

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
        request.setAttribute(ORDER_CANCELED, "order.canceled");
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
