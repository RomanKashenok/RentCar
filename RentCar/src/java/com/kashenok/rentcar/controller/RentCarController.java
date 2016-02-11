package com.kashenok.rentcar.controller;

import com.kashenok.rentcar.command.ICommand;
import com.kashenok.rentcar.exception.CommandException;
import com.kashenok.rentcar.command.CommandFactory;
import com.kashenok.rentcar.database.pool.ConnectionPool;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * The Class RentCarController. Receive requests on /controller direction,
 * causes the needed action and forwards to the result page.
 */
@WebServlet(name = "RentCarController", initParams = {
    @WebInitParam(name = "log4j", value = "/WEB-INF/log4j.properties")}, urlPatterns = "/controller")
public class RentCarController extends HttpServlet {

    public static final Logger LOG = Logger.getLogger(RentCarController.class);
    private static final long serialVersionUID = -6337638379291563527L;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request is request
     * @param response is response
     * @throws UnsupportedEncodingException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String page = null;
        CommandFactory commandFactory = new CommandFactory();
        try {
            ICommand command = commandFactory.defineCommand(request);
            page = command.execute(request);
            if (page != null && !page.isEmpty()) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                try {
                    dispatcher.forward(request, response);
                } catch (ServletException ex) {
                    LOG.error("Impossible to process RequestDispatcher 'forward' command: ", ex);
                    response.sendError(500);
                }
            } else {
                LOG.error("Page is unavailible, absent, empty or null");
                response.sendError(500);
            }
        } catch (CommandException | IOException ex) {
            LOG.error("Exception in executing page process: ", ex);
        }
    }

    /**
     * The method receive HTTP <code>GET</code> and call processRequest method
     *
     * @param request is the request
     * @param response is the response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * The method receive HTTP <code>POST</code> and call processRequest method
     *
     * @param request is the request
     * @param response is the response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * The method initializes log4j
     *
     * @param config is the configuration of the servlet
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String log4j = config.getInitParameter("log4j");
        String path = getServletContext().getRealPath(log4j);
        PropertyConfigurator.configure(path);
    }

    /**
     * The method performs clean up the connectionPool when the servlet stops
     */
    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().clearPool();

    }
}
