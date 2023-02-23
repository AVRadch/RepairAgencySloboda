package com.my.repairagency007.controller.command.common;

import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServiceException {

        HttpSession session = httpRequest.getSession();
        if (session.getAttribute("logged_user") != null) {
            String locale = (String) session.getAttribute("locale");
            session.invalidate();
            httpRequest.getSession(true).setAttribute("locale", locale);
        }
        return "login.jsp";
    }
}
