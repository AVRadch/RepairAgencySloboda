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

import java.io.IOException;

import static com.my.repairagency007.controller.command.CommandUtility.moveAttributeFromSessionToRequest;
import static com.my.repairagency007.util.MapperDTOUtil.fillUserDTO;

public class UpdateUserCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserCommand.class);

    private final UserServiceImpl userService;

    public UpdateUserCommand() {userService = AppContext.getAppContext().getUserService();}

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

    private String executePost(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
 //       RequestDispatcher dispatcher;

        log.debug("Get id user for update " + request.getParameter("user-id"));
        int id = Integer.parseInt(request.getParameter("user-id"));
        UserDTO userDTO = userService.getById(id);
        log.debug("Get user for update " + userDTO.getLastName());
        log.debug("User role = " + userDTO.getRole());
        log.debug("Role from form ==> " + request.getParameter("role"));
        fillUserDTO(request, userDTO);
        userDTO.setRole(request.getParameter("role"));
        userDTO.setAccount(request.getParameter("account").replace(",", "."));

        String forward = "controller?action=adminAllUsers";

        userService.update(userDTO);

        request.getSession().setAttribute("message", "label.succesUpdate");
        request.setAttribute("userDTO", userDTO);

  //      try {
//            response.sendRedirect(forward);
//            forward = "controller?action=redirect";
 //           return forward;
   //     } catch (IOException e) {
   //         log.error("Error user update command", e);
   //         forward = "error_page.jsp";
   //     }

        //       dispatcher = request.getRequestDispatcher("editUser.jsp");


        return forward;
    }
}
