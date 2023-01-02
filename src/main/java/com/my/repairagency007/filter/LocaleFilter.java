package com.my.repairagency007.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/",
initParams = @WebInitParam(name = "defaultLocale", value = "en"))
public class LocaleFilter implements Filter {
    private String defaultLocale;

    @Override
    public void init(FilterConfig config) {
        defaultLocale = config.getInitParameter("defaultLocale");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String locale = httpRequest.getParameter("locale");
        if (locale == null || locale.isEmpty()) {
            httpRequest.getSession().setAttribute("locale", locale);
            ((HttpServletResponse)response).setIntHeader("Refresh", 0);
            if ((!httpRequest.getServletPath().contains("controller"))) {
                chain.doFilter(request, response);
            }
        } else {
            String sessionLocale = (String) httpRequest.getSession().getAttribute("locale");
            if (sessionLocale == null || sessionLocale.isEmpty()) {
                httpRequest.getSession().setAttribute("locale", defaultLocale);
            }
            chain.doFilter(request, response);
        }
    }
}