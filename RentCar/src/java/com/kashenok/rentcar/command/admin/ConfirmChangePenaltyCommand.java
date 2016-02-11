package com.kashenok.rentcar.command.admin;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.PenaltyService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import javax.servlet.http.HttpServletRequest;

/**
 * The class ConfirmChangePenaltyCommand. Make and write changes in penalty
 * object fields
 */
public class ConfirmChangePenaltyCommand implements ICommand {

    public static final String PENALTY_ID = "penaltyId";
    public static final String PENALTY_MESSAGE = "penaltyMessage";
    public static final String PENALTY_SUM = "sum";
    public static final String PENALTY_UPDATE_FAILED = "penaltyUpdateFailed";
    public static final String PENALTY_UPDATED = "penaltyUpdated";

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
        boolean isUpdated = false;

        try {
            long penaltyId = Long.parseLong(request.getParameter(PENALTY_ID));
            String newMessage = request.getParameter(PENALTY_MESSAGE);
            double newSum = Double.parseDouble(request.getParameter(PENALTY_SUM));
            isUpdated = penaltyService.updatePenaltyById(penaltyId, newMessage, newSum);
            if (!isUpdated) {
                request.setAttribute(PENALTY_UPDATE_FAILED, "penalty.update.failed");
                return ConfigurationManager.getInstance().getProperty("page.main");
            }
            RefSessionCleaner.cleanAttributes(request);
            request.setAttribute(PENALTY_UPDATED, "penalty.updated");

        } catch (ServiceException ex) {
            throw new CommandException("Impossible process execute method in ConfirmChangePenaltyCommand class: ", ex);
        }

        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
