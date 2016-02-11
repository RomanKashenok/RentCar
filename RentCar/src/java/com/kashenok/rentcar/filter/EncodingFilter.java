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

/**
 * The Class EncodingFilter changes the encoding for the request and response to
 * UTF-8.
 */
@WebFilter(urlPatterns = {"/*"}, initParams = {
    @WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")})
public class EncodingFilter implements Filter {

    private String code;

    /**
     * The method init from javax.servlet.Filter
     *
     * @param filterConfig is the FilterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        code = filterConfig.getInitParameter("encoding");
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
        String codeRequest = request.getCharacterEncoding();
        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
        }
        chain.doFilter(request, response);
    }

    /**
     * The method destroy from javax.servlet.Filter
     */
    @Override
    public void destroy() {
        code = null;
    }

}
