package com.kashenok.rentcar.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The class UserFilter redirects the call to the main page with direct
 * reference to jsp
 */
@WebFilter(urlPatterns = {"/pages/*"}, initParams = {
    @WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class UserFilter implements Filter {

    private String mainPage;

    /**
     * The method init from javax.servlet.Filter
     *
     * @param filterConfig is the FilterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        mainPage = filterConfig.getInitParameter("INDEX_PATH");
    }

    /**
     * The method doFilter from javax.servlet.Filter
     *
     * @param request is the ServletRequest
     * @param response is the ServletResponse
     * @param chain is the FilterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + mainPage);
        chain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * The method destroy from javax.servlet.Filter
     */
    @Override
    public void destroy() {
    }

}
