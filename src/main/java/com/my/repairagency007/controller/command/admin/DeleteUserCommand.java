package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.my.repairagency007.controller.command.CommandUtility.moveAttributeFromSessionToRequest;

public class DeleteUserCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(DeleteUserCommand.class);

    private final UserServiceImpl userService;

    public DeleteUserCommand(AppContext appContext) {userService = appContext.getUserService();}

    @Override
    public String execute(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServiceException {

        log.info("get request method" + httpRequest.getMethod());
        return httpRequest.getMethod().equals("POST") ? executePost(httpRequest) : executeGet(httpRequest);
    }

    private String executeGet(HttpServletRequest request) {
        log.info("Execute delete request Get");
        moveAttributeFromSessionToRequest(request, "message");
        moveAttributeFromSessionToRequest(request, "error");
        log.info("request error = " + request.getAttribute("error"));
        return "controller?action=adminAllUsers";
    }

    private String executePost(HttpServletRequest httpRequest) {

        HttpSession session = httpRequest.getSession();
        int id = Integer.parseInt(httpRequest.getParameter("user-id"));
        log.info("Delete user with id = " + id);
        String forward = "controller?action=adminAllUsers";
        try {
            userService.delete(id);
            session.setAttribute("message", "message.successDelete");
        } catch (ServiceException e) {
            session.setAttribute("error", "error.errorDelete");
        }
        return forward;
    }
}
