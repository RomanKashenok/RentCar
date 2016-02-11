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
 * The class ShowPenaltyByStatusCommand. Get all Penalty entries with selected
 * status.
 */
public class ShowPenaltyByStatusCommand implements ICommand {

    public static final String ADMIN_PAID_PENALTY_LIST = "adminStatusPenList";
    public static final String PENALTY_GET_ERROR = "penaltyGetError";
    public static final String PENALTY_STATUS = "penaltyStatus";

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
        boolean isPaid = Boolean.parseBoolean(request.getParameter(PENALTY_STATUS));
        try {
            List<Penalty> unpaidPenaltyList = penaltyService.findPenaltyByStatus(isPaid);
            if (unpaidPenaltyList.isEmpty() || unpaidPenaltyList == null) {
                request.setAttribute(PENALTY_GET_ERROR, "penalty.list.empty");
                return ConfigurationManager.getInstance().getProperty("page.main");
            }
            RefSessionCleaner.cleanAttributes(request);
            request.getSession().setAttribute(ADMIN_PAID_PENALTY_LIST, unpaidPenaltyList);
        } catch (ServiceException ex) {
            throw new CommandException("Impossible process execute method in AdminPenaltyConfirmCommand class: ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }
}
