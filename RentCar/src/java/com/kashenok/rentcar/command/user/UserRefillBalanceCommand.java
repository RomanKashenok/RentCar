package com.kashenok.rentcar.command.user;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.entity.User;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.UserService;
import com.kashenok.rentcar.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;

/**
 * The class UserRefillBalanceCommand. Set and write new balance value to user.
 */
public class UserRefillBalanceCommand implements ICommand {

    public static final String REFILL_BALANCE = "refillBalance";
    public static final String USER_BALANCE = "userBalance";
    public static final String REFILL_BALANCE_FAILED = "refillFailedMessage";
    public static final String USER_ID = "userId";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        User user = new User();
        UserService userService = new UserService();
        boolean isRefilled = false;
        int userId = (int) request.getSession().getAttribute(USER_ID);
        double currentBalance = (double) request.getSession().getAttribute(USER_BALANCE);
        double refillSum = Double.parseDouble(request.getParameter(REFILL_BALANCE));
        try {
            isRefilled = userService.refillBalance(currentBalance, refillSum, userId);
            if (isRefilled) {
                double userBalance = userService.findUserRefilledBalance(userId, currentBalance);
                request.getSession().setAttribute(USER_BALANCE, userBalance);
            } else {
                request.setAttribute(REFILL_BALANCE_FAILED, "balance.refill.failed");
            }

        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }

        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
