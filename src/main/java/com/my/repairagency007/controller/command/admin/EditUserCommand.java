package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.my.repairagency007.controller.command.CommandUtility.moveAttributeFromSessionToRequest;

public class EditUserCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(EditUserCommand.class);

    private final UserServiceImpl userService;

    public EditUserCommand(AppContext appContext) {userService = appContext.getUserService();}

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        int id = Integer.parseInt(request.getParameter("user-id"));
        UserDTO userDTO = userService.getById(id);
        request.setAttribute("userDTO", userDTO);
        log.debug("Get user" + userDTO.getLastName());
        return "editUser.jsp";
    }
}
