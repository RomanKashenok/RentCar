package com.kashenok.rentcar.command.user;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.entity.RentTermHolder;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.OrderService;
import com.kashenok.rentcar.exception.ServiceException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * The class OrderChangeCommand. Changes selected order.
 */
public class OrderChangeCommand implements ICommand {

    public static final String USER_ID = "userId";
    public static final String ORDER_ID = "orderId";
    public static final String CAR_ID = "carId";

    public static final String ORDER_EDIT = "orderEdit";
    public static final String ORDER_EDIT_COAST = "orderEditCoast";
    public static final String ORDER_EDITED_COAST = "orderEditedCoast";

    public static final String ORDER_EDITED = "orderEdited";

    public static final String BALANCE_DIFFERENCE_CREDIT = "balanceDifferenceCredit";
    public static final String BALANCE_DIFFERENCE_REFUSE = "balanceDifferenceRefuse";

    public static final String DATE_INCORRECT = "dateIncorrect";
    public static final String START_DATE_INCORRECT = "startDateIncorrect";
    public static final String ORDER = "order";
    public static final String DATE_BUSY = "orderedDateBusy";
    public static final String CONFIRMED_ORDER = "confirmedOrder";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        OrderService orderService = new OrderService();
        Date todayDate = new Date(Calendar.getInstance().getTime().getTime());
        List<RentTermHolder> orderedDatesList = new ArrayList<>();
        Map<String, String> parameters = new HashMap<String, String>();

        try {
            String userId = String.valueOf(request.getSession().getAttribute(USER_ID));
            int orderEditedId = Integer.parseInt(request.getParameter(ORDER_ID));
            Order orderEdit = (Order) request.getSession().getAttribute(ORDER_EDIT);
            double orderEditCoast = orderEdit.getCoast();
            Enumeration<String> parameterNames = request.getParameterNames();
            int carId = Integer.parseInt(request.getParameter(CAR_ID));

            orderedDatesList = orderService.findCarOrderedDates(carId);
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);
                for (int i = 0; i < paramValues.length; i++) {
                    String paramValue = paramValues[i];
                    parameters.put(paramName, paramValue);
                }
            }
            parameters.put(USER_ID, userId);
            Order orderEdited = orderService.createOrder(parameters);
            if (orderEdited != null) {
                orderEdited.setOrderId(orderEditedId);
                if (orderEdit.getCoast() >= orderEdited.getCoast()) {
                    request.getSession().setAttribute(BALANCE_DIFFERENCE_CREDIT, "order.change.balanceDiference.credit");
                } else {
                    request.getSession().setAttribute(BALANCE_DIFFERENCE_REFUSE, "order.change.balanceDiference.refuse");
                }
                if (todayDate.after(orderEdited.getDateFrom())) {
                    request.setAttribute(START_DATE_INCORRECT, "order.startDate.incorrect");
                    return ConfigurationManager.getInstance().getProperty("page.main");
                }
                boolean flag = false;
                for (int i = 0; i < orderedDatesList.size(); i++) {
                    if (orderEdited.getDateFrom().before(orderedDatesList.get(i).getDateFrom()) && orderEdited.getDateTo().before(orderedDatesList.get(i).getDateFrom())) {
                        flag = true;
                    } else if (orderEdited.getDateFrom().after(orderedDatesList.get(i).getDateTo()) && orderEdited.getDateTo().after(orderedDatesList.get(i).getDateTo())) {
                        flag = true;
                    } else {
                        flag = false;
                        request.setAttribute(DATE_BUSY, "ordered.date.busy");
                        request.setAttribute(CONFIRMED_ORDER, orderedDatesList.get(i));
                        return ConfigurationManager.getInstance().getProperty("page.main");
                    }
                }

                request.getSession().setAttribute(ORDER_EDITED, orderEdited);
                request.getSession().setAttribute(ORDER_EDIT_COAST, orderEditCoast);
                request.getSession().setAttribute(ORDER_EDITED_COAST, orderEdited.getCoast());

            } else {
                request.setAttribute(DATE_INCORRECT, "order.date.incorrect");
                return ConfigurationManager.getInstance().getProperty("page.main");
            }

        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
