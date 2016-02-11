package com.kashenok.rentcar.command.admin;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.entity.Penalty;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.PenaltyService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import javax.servlet.http.HttpServletRequest;

/**
 * The class NeedChangePenaltyCommand. Redirect user to page with change Penalty
 * form
 */
public class NeedChangePenaltyCommand implements ICommand {

    public static final String PENALTY_CHANGE = "penaltyChange";
    public static final String PENALTY_ID = "penaltyId";
    public static final String PENALTY_FIND_ERROR = "penaltyFindError";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        PenaltyService penaltyService = new PenaltyService();
        try {
            long penaltyId = Integer.parseInt(request.getParameter(PENALTY_ID));
            Penalty penaltyChange = penaltyService.findPenaltyById(penaltyId);
            if (penaltyChange != null) {
                RefSessionCleaner.cleanAttributes(request);
                request.setAttribute(PENALTY_CHANGE, penaltyChange);
            } else {
                request.setAttribute(PENALTY_FIND_ERROR, "penalty.get.error");
            }
        } catch (ServiceException ex) {
            throw new CommandException("Impossible process execute method in NeedChangePenaltyCommand class: ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
