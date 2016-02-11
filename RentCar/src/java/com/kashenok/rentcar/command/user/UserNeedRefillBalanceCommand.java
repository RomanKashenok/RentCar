package com.kashenok.rentcar.command.user;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import javax.servlet.http.HttpServletRequest;

/**
 * The class UserNeedRefillBalanceCommand. Show field to refill user balance.
 */
public class UserNeedRefillBalanceCommand implements ICommand {

    public static final String REFILL_BALANCE = "refillBalance";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        request.setAttribute(REFILL_BALANCE, "refillBalance");
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
