package com.kashenok.rentcar.command.admin;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.entity.Penalty;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.PenaltyService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * The class ShowPenaltyCommand. Get all Penalty entries
 */
public class ShowPenaltyCommand implements ICommand {

    public static final String ADMIN_PENALTY_LIST = "adminPenList";
    public static final String PENALTY_GET_ERROR = "penaltyGetError";

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
            List<Penalty> penaltyList = penaltyService.findAllPenalty();
            if (penaltyList.isEmpty() || penaltyList == null) {
                request.setAttribute(PENALTY_GET_ERROR, "penalty.get.error");
                return ConfigurationManager.getInstance().getProperty("page.main");
            }
            RefSessionCleaner.cleanAttributes(request);
            request.getSession().setAttribute(ADMIN_PENALTY_LIST, penaltyList);
        } catch (ServiceException ex) {
            throw new CommandException("Impossible process execute method in AdminPenaltyConfirmCommand class: ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
