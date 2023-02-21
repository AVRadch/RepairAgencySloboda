package com.my.repairagency007.controller.command.admin;


import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.IncorrectFormatException;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.model.entity.Role;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.my.repairagency007.util.MapperDTOUtil.fillUserDTO;

/**
 * New user registration controller command.
 *
 * @author Aleksey Serdyukov
 */
public class RegistrationCommand implements Command {

  private static final Logger log = LoggerFactory.getLogger(RegistrationCommand.class);

  private final UserServiceImpl userService;

  public RegistrationCommand() {
    userService = AppContext.getAppContext().getUserService();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {

    log.debug("Start registration command");
    HttpSession session = request.getSession();
    String resp;
    UserDTO userDTO = UserDTO.builder().build();
    try {
      fillUserDTO(request, userDTO);
    } catch (IncorrectFormatException e) {
      session.setAttribute("error", e.getMessage());
    }
    userDTO.setRole(Role.UNREGISTRED.getName());
    userDTO.setAccount("0");
    userDTO.setPassword(BCrypt.hashpw(request.getParameter("password").trim(), BCrypt.gensalt()));
    resp = "login.jsp";
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
