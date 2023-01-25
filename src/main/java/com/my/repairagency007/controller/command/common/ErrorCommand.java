package com.my.repairagency007.controller.command.common;

import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorCommand implements Command {

    @Override
    public String execute(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServiceException {
        return "error_page.jsp";
    }
}
