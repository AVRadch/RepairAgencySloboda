package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmDeleteUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServiceException {

        int id = Integer.parseInt(httpRequest.getParameter("user-id"));
        String forward = "controller?action=adminAllUsers";
        String back = "controller?action=adminAllUsers";
        return null;
    }
}
