package com.kashenok.rentcar.command.user;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.entity.Penalty;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.PenaltyService;
import com.kashenok.rentcar.service.UserService;
import com.kashenok.rentcar.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;

/**
 * The class PenaltyPayCommand. Changes Penalty status to 'payed', changes Users
 * balance.
 */
public class PenaltyPayCommand implements ICommand {

    public static final String PENALTY = "penalty";
    public static final String USER_ID = "userId";
    public static final String USER_BALANCE = "userBalance";
    public static final String NEED_MORE_MONEY = "balanceDeficit";
    public static final String PENALTY_PAYED = "penaltyPayed";
    public static final String CONFIRM_PENALTY_FAILED = "penaltyConfirmFailed";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        boolean isPayed = false;
        boolean isBalanceUpdated = false;
        try {
            int userId = (int) request.getSession().getAttribute(USER_ID);
            Penalty penalty = (Penalty) request.getSession().getAttribute(PENALTY);
            double userBalance = (double) request.getSession().getAttribute(USER_BALANCE);
            double penaltySum = penalty.getSum();
            if (penaltySum > userBalance) {
                request.setAttribute(NEED_MORE_MONEY, "user.balance.daficit");
            } else {
                PenaltyService penaltyService = new PenaltyService();
                UserService userService = new UserService();
                isPayed = penaltyService.changePenaltyStatus(penalty, true);
                if (isPayed) {
                    request.getSession().removeAttribute(PENALTY);
                    request.setAttribute(PENALTY_PAYED, "penalty.payed");
                    isBalanceUpdated = userService.updateBalance(userId, userBalance, penaltySum);
                    if (isBalanceUpdated) {
                        userBalance = userService.findUserRefilledBalance(userId, userBalance);
                        request.getSession().setAttribute(USER_BALANCE, userBalance);
                    }
                } else {
                    request.setAttribute(CONFIRM_PENALTY_FAILED, "penalty.pay.failed");
                }
            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
