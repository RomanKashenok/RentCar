package com.kashenok.rentcar.command.user;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.entity.Order;
import com.kashenok.rentcar.entity.RentTermHolder;
import com.kashenok.rentcar.resource.ConfigurationManager;
import com.kashenok.rentcar.service.OrderService;
import com.kashenok.rentcar.exception.ServiceException;
import com.kashenok.rentcar.util.RefSessionCleaner;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * The class OrderCreateCommand. Creates new order
 */
public class OrderCreateCommand implements ICommand {

    public static final String DATE_INCORRECT = "dateIncorrect";
    public static final String START_DATE_INCORRECT = "startDateIncorrect";
    public static final String ORDERED_DAYS = "orderedDatesList";
    public static final String DATE_BUSY = "orderedDateBusy";
    public static final String CONFIRMED_ORDER = "confirmedOrder";
    

    public static final String ORDER = "order";

    /**
     * The method execute.
     *
     * @param request is the request
     * @return the String - path to required page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        Date todayDate = new Date(Calendar.getInstance().getTime().getTime());
        OrderService orderService = new OrderService();
        try {
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);
                for (int i = 0; i < paramValues.length; i++) {
                    String paramValue = paramValues[i];
                    parameters.put(paramName, paramValue);
                }
            }
            Order order = orderService.createOrder(parameters);
            ArrayList<RentTermHolder> orderedDatesList = (ArrayList<RentTermHolder>) request.getSession().getAttribute(ORDERED_DAYS);
            if (order != null) {
                
                
                if (todayDate.after(order.getDateFrom())) {
                    request.setAttribute(START_DATE_INCORRECT, "order.startDate.incorrect");
                    return ConfigurationManager.getInstance().getProperty("page.main");
                }
                boolean flag = false;
                for (int i = 0; i < orderedDatesList.size(); i++) {
                    if (order.getDateFrom().before(orderedDatesList.get(i).getDateFrom()) && order.getDateTo().before(orderedDatesList.get(i).getDateFrom())) {
                        flag = true;
                    } else if (order.getDateFrom().after(orderedDatesList.get(i).getDateTo()) && order.getDateTo().after(orderedDatesList.get(i).getDateTo())) {
                        flag = true;
                    } else {
                        flag = false;
                        request.setAttribute(DATE_BUSY, "ordered.date.busy");
                        request.setAttribute(CONFIRMED_ORDER, orderedDatesList.get(i));
                        return ConfigurationManager.getInstance().getProperty("page.main");
                    }
                }
                
                
                
                RefSessionCleaner.cleanAttributes(request);
                request.getSession().setAttribute(ORDER, order);

            } else {
                request.setAttribute(DATE_INCORRECT, "order.date.incorrect");
                return ConfigurationManager.getInstance().getProperty("page.main");
            }
        } catch (ServiceException ex) {
            throw new CommandException("Impossible to prepare execute command in OrderCreateCommand ", ex);
        }
        return ConfigurationManager.getInstance().getProperty("page.main");
    }

}
