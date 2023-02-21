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
import javax.servlet.http.HttpSession;

import static com.my.repairagency007.controller.command.CommandUtility.moveAttributeFromSessionToRequest;
/**
 * This is EditUserCommand class. Accessible by admin.
 * Get user from DB for edtitng in controller
 *
 * @author Alex Radchenko
 * @version 1.0
 */
public class EditUserCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(EditUserCommand.class);

    private final UserServiceImpl userService;
    /**
     * @param appContext using for get the value of UserServiceImpl instance to use in command
     */
    public EditUserCommand(AppContext appContext) {userService = appContext.getUserService();}
    /**
     * Method get user from DB and save it in request attribute and return editUser page
     *
     * @param request to get users id for get user from DB and save it in request attribute
     * @return editUser page for redirect
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("user-id"));
        UserDTO userDTO = userService.getById(id);
        session.setAttribute("userDTO", userDTO);
        log.info("Get user => " + userDTO);
        return "editUser.jsp";
    }
}
