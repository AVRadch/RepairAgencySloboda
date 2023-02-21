package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.IncorrectFormatException;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.my.repairagency007.controller.command.CommandUtility.moveAttributeFromSessionToRequest;
import static com.my.repairagency007.util.MapperDTOUtil.fillUserDTO;
import static com.my.repairagency007.util.ValidatorUtil.validateAccount;

public class UpdateUserCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserCommand.class);

    private final UserServiceImpl userService;

    public UpdateUserCommand(AppContext appContext) {userService = appContext.getUserService();}

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        log.debug("get request method" + request.getMethod());
        return request.getMethod().equals("POST") ? executePost(request, response) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        moveAttributeFromSessionToRequest(request, "message");
        moveAttributeFromSessionToRequest(request, "error");
        return "editUser.jsp";
    }

    private String executePost(HttpServletRequest request, HttpServletResponse response) {

        int id = Integer.parseInt(request.getParameter("user-id"));
        log.info("Get id user for update " + id);
        HttpSession session = request.getSession();
        UserDTO userDTO = null;
        String errorMessage;
        try {
            userDTO = userService.getById(id);
        } catch (ServiceException e) {
            errorMessage = "error.updateDB";
            log.info(errorMessage);
            session.setAttribute("error", errorMessage);
            return "controller?action=updateUser";
        }
        try {
            fillUserDTO(request, userDTO);
            userDTO.setRole(request.getParameter("role"));
            userDTO.setAccount(request.getParameter("account").replace(",", "."));
            validateAccount(userDTO.getAccount());
        } catch (IncorrectFormatException e) {
            session.setAttribute("error", e.getMessage());
            return "controller?action=updateUser";
        }

        String forward = "controller?action=adminAllUsers";

        try {
            userService.update(userDTO);
            request.getSession().setAttribute("message", "label.succesUpdate");
            request.setAttribute("userDTO", userDTO);
        } catch (ServiceException e) {
            errorMessage = "error.updateDB";
            log.info(errorMessage);
            session.setAttribute("error", errorMessage);
            return "controller?action=updateUser";
        }
        return forward;
    }
}
