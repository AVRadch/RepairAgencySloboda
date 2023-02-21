package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.Path;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.IncorrectFormatException;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.my.repairagency007.util.MapperDTOUtil.fillUserDTO;
/**
 * This is RegistrationAdminCommand class. Accessible by admin.
 * Get information from request, fill userDTO and create user in DB
 *
 * @author Alex Radchenko
 * @version 1.0
 */
public class RegistrationAdminCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(RegistrationAdminCommand.class);

    private final UserServiceImpl userService;
    /**
     * @param appContext using for get the value of UserServiceImpl instance to use in command
     */
    public RegistrationAdminCommand(AppContext appContext) {userService = appContext.getUserService();}
    /**
     * Method get users information from request, fill userDTO and create user in DB
     *
     * @param request to get users information
     * @return command for controller
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        log.debug("Start registration admin command");
        HttpSession session = request.getSession();
        UserDTO userDTO = UserDTO.builder().build();
        String resp = "controller?action=adminAllUsers";
        try {
            fillUserDTO(request, userDTO);
        } catch (IncorrectFormatException e) {
            session.setAttribute("error", e.getMessage());
        }
        userDTO.setRole(request.getParameter("role"));
        userDTO.setAccount("0");
        userDTO.setPassword(BCrypt.hashpw(request.getParameter("password").trim(), BCrypt.gensalt()));

        try {
            log.debug("Try to create new user in Data Base");
            userService.create(userDTO);
        } catch (ServiceException e) {
            log.error("Error registration new user", e);
            resp = "error_page.jsp";
        }
        return resp;
    }
}
