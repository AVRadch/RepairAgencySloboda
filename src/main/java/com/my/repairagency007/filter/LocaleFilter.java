package com.my.repairagency007.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/",
initParams = @WebInitParam(name = "defaultLanguage", value = "en"))
public class LocaleFilter implements Filter {
    private String defaultLanguage;

    @Override
    public void init(FilterConfig config) {
        defaultLanguage = config.getInitParameter("defaultLanguage");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String language = httpRequest.getParameter("language");
        if (language == null || language.isEmpty()) {
            httpRequest.getSession().setAttribute("language", language);
            ((HttpServletResponse)response).setIntHeader("Refresh", 0);
            if ((!httpRequest.getServletPath().contains("controller"))) {
                chain.doFilter(request, response);
            }
        } else {
            String sessionLocale = (String) httpRequest.getSession().getAttribute("language");
            if (sessionLocale == null || sessionLocale.isEmpty()) {
                httpRequest.getSession().setAttribute("language", defaultLanguage);
            }
            chain.doFilter(request, response);
        }
    }
}