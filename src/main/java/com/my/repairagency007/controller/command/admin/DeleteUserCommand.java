package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.controller.Path;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUserCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(DeleteUserCommand.class);

    private final UserServiceImpl userService;

    public DeleteUserCommand(AppContext appContext) {userService = appContext.getUserService();}


    @Override
    public String execute(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServiceException {

        int id = Integer.parseInt(httpRequest.getParameter("user-id"));
        log.debug("Delete user with id = " + id);
        String forward = "controller?action=adminAllUsers";
        userService.delete(id);
        try {
            httpResponse.sendRedirect(forward);
            forward = Path.COMMAND_REDIRECT;
        } catch (IOException e) {
            forward = Path.PAGE_ERROR_PAGE;
        }
        return forward;
    }
}
