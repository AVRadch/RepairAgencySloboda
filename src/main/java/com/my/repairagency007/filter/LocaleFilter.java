package com.my.repairagency007.filter;

import com.my.repairagency007.controller.listener.ContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*",
initParams = @WebInitParam(name = "defaultLanguage", value = "en"))
public class LocaleFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(LocaleFilter.class);
    private String defaultLanguage;

    @Override
    public void init(FilterConfig config) {
        defaultLanguage = config.getInitParameter("defaultLanguage");
        log.debug("Init default language = " + defaultLanguage);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String language = httpRequest.getParameter("language");
        log.debug("language from request = " + language);
        if (language != null && !language.isEmpty()) {
            httpRequest.getSession().setAttribute("language", language);
            log.debug("Set initial language " + language + " to session");
            ((HttpServletResponse)response).setIntHeader("Refresh", 0);
            if ((!httpRequest.getServletPath().contains("controller"))) {
                chain.doFilter(request, response);
            }
        } else {
            String sessionLocale = (String) httpRequest.getSession().getAttribute("language");
            log.debug("Else get language from session = " + sessionLocale);
            if (sessionLocale == null || sessionLocale.isEmpty()) {
                httpRequest.getSession().setAttribute("language", defaultLanguage);
                log.debug("Session locale also empty, set default language = " + defaultLanguage);
            }
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        log.debug("Filter destruction");
    }
}