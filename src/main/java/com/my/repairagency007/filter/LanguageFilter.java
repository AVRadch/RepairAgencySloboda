package com.my.repairagency007.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * @author sburch
 * @version 1.0
 * This filter makes encoding with charset utf-8.
 */
@WebFilter(urlPatterns = "/*",
initParams = @WebInitParam(name = "characterEncoding", value = "UTF-8"))
public class LanguageFilter implements Filter {
    private String encoding;
    private ServletContext context;

    /**
     * Default constructor
     */
    public LanguageFilter() {
    }

    /**
     * Method cleans resource
     */
    public void destroy() {
    }

    /**
     * Method makes filter by chain on request and response
     *
     * @param request
     * @param response
     * @param chain
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        context.log("charset set");
        chain.doFilter(request, response);
    }

    /**
     * Method calls to indicate a filter config
     *
     * @param config
     */
    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("characterEncoding");
        context = config.getServletContext();
    }
}
