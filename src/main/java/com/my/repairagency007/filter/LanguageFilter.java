package com.my.repairagency007.filter;

import com.my.repairagency007.controller.command.admin.UpdateRequestAdminCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 *Created by IntelliJ IDEA.
 *User: Алексей Радченко
 *Date: 09.01.2023
 *Time: 15:25
 *To change this template use File | Settings | File Templates.
 * This filter makes encoding with charset utf-8.
 */
@WebFilter(urlPatterns = "/*",
initParams = @WebInitParam(name = "characterEncoding", value = "UTF-8"))
public class LanguageFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(LanguageFilter.class);
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
        log.debug("Language filter work");
 //       context.log("charset set");
        chain.doFilter(request, response);
    }

    /**
     * Method calls to indicate a filter config
     *
     * @param config
     */
    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("characterEncoding");
        log.debug("Set encoding filter to " + encoding);
        context = config.getServletContext();
    }
}
