package com.my.repairagency007.controller.command.common;

import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.ResourceBundle;

import com.my.repairagency007.model.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(LoginCommand.class);

    private final UserService userService;
    private ResourceBundle resourceBundle;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse httpResponse) {

        HttpSession session = request.getSession();
 //       resourceBundle = ResourceBundle.getBundle("property/messages", CommandUtility.getSessionLocale(request));

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        log.debug("email + password" + email + password);
        String errorMessage;

        if(email == null || password == null || email.isEmpty() || password.isEmpty()){
            errorMessage = "Login or password can't be empty";
            log.debug(errorMessage);
            request.setAttribute("errorMessage", errorMessage);
            return "login.jsp";
        }
        User user;

        try {
            user = userService.getByEmail(email);
        }catch (Exception e){
            e.printStackTrace();
            log.error("error in getByEmail method", e);
            request.setAttribute("message_er", resourceBundle.getString("login.invalid"));
            return "login.jsp";
        }

        if (Objects.isNull(user)) {
            log.debug("user is null");
            request.setAttribute("message_er", resourceBundle.getString("login.invalid"));
            return "login.jsp";
        }

        if (BCrypt.checkpw(password, user.getPassword())) {
     //       HttpSession session = request.getSession();
            log.debug("try to save user attribute in session");
            session.setAttribute("user", user);
            session.setAttribute("email", user.getEmail());
            session.setAttribute("balance", user.getAccount());
            session.setAttribute("roles", user.getRoleId());
            return "controller?action=adminAllRequest";
        } else {
            log.debug("not correct password");
            request.setAttribute("message_er", resourceBundle.getString("login.invalid"));
            return "login.jsp";
        }
    }
}
