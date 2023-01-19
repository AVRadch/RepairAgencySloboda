package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.Path;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.my.repairagency007.util.MapperDTOUtil.fillUserDTO;

public class RegistrationAdminCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(RegistrationAdminCommand.class);

    private final UserServiceImpl userService;

    public RegistrationAdminCommand() {userService = AppContext.getAppContext().getUserService();}

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        log.debug("Start registration admin command");
        UserDTO userDTO = UserDTO.builder().build();
        String resp = "controller?action=adminAllUsers";
        fillUserDTO(request, userDTO);
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
