package com.my.repairagency007.controller.command.common;

import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.command.admin.UpdateUserCommand;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.IncorrectPasswordException;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.DAO.implementations.UserDAOImpl;
import com.my.repairagency007.model.entity.Role;
import com.my.repairagency007.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.ResourceBundle;

import com.my.repairagency007.model.services.UserService;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.my.repairagency007.controller.command.CommandUtility.moveAttributeFromSessionToRequest;

public class LoginCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(LoginCommand.class);

    private final UserServiceImpl userService;

    public LoginCommand(AppContext appContext) {userService = appContext.getUserService();}

    private ResourceBundle resourceBundle;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        log.debug("get request method" + request.getMethod());
        return request.getMethod().equals("POST") ? executePost(request, response) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        log.info("Execute login Get");
        moveAttributeFromSessionToRequest(request, "message");
        moveAttributeFromSessionToRequest(request, "error");
        log.info("request error = " + request.getAttribute("error"));
        return "login.jsp";
    }

    private String executePost(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        log.info("email + password = " + email + password);
        String errorMessage;

        if(email == null || email.isEmpty()){
            errorMessage = "error.emailEmpty";
            log.info(errorMessage);
            session.setAttribute("error", errorMessage);
            return "controller?action=login";
        } else if (password == null || password.isEmpty()){
            errorMessage = "error.passwordEmpty";
            session.setAttribute("error", errorMessage);
            return "controller?action=login";
        }
        UserDTO userDTO;
        try {
            log.info("Try to login");
            userDTO = userService.login(email, password);
            log.info("check the password");
            if (!BCrypt.checkpw(password, userDTO.getPassword())){
                throw new IncorrectPasswordException();
            }
        }catch (Exception e){
            session.setAttribute("error", e.getMessage());
            session.setAttribute("email", email);
            log.error("error in getByEmail method", e);
            return "controller?action=login";
        }
            log.debug("try to save user attribute in session");
            session.setAttribute("logged_user", userDTO);
            session.setAttribute("logged_user_role", userDTO.getRole());
            String forward;

        switch (userDTO.getRole().toLowerCase()){
            case "manager": forward = "controller?action=adminAllRequest";
                            break;
            case "craftsman": forward = "controller?action=craftsmanRequest";
                            break;
            case "user": forward = "controller?action=userRequest";
                            break;
            default: forward = "login.jsp";
        }
            return forward;
     }
}
