package com.my.repairagency007.controller.command.common;

import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LocaleHandlerCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(LocaleHandlerCommand.class);
    @Override
    public String execute(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServiceException {

        String lang = httpRequest.getParameter("language");
        log.debug("Parametr language = " + lang);
        httpRequest.getSession().setAttribute("language", lang);
        String referer =  httpRequest.getHeader("referer");
        try {
            log.debug("Try to redirect" + referer);
            httpResponse.sendRedirect(referer);
        } catch (IOException e) {
            log.error("Error redirect in LocalHandler Command", e);
            return "error_page.jsp";
        }
        return "redirect";
    }
}
