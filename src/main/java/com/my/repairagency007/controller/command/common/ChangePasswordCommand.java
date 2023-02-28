package com.my.repairagency007.controller.command.common;

import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.IncorrectPasswordException;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import com.my.repairagency007.util.ValidatorUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;

import static com.my.repairagency007.controller.command.CommandUtility.moveAttributeFromSessionToRequest;

public class ChangePasswordCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(ChangePasswordCommand.class);

    private final UserServiceImpl userService;

    public ChangePasswordCommand(AppContext appContext) {
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return request.getMethod().equals("POST")? executePost(request): executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        moveAttributeFromSessionToRequest(request, "message");
        moveAttributeFromSessionToRequest(request, "error");
        return "changePassword.jsp";
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        HttpSession session = request.getSession();
        String password = request.getParameter("old-password");
        String errorMessage;
        if (password == null || password.isEmpty()) {
            return getString("error.passwordEmpty", session);
        }

        UserDTO user = (UserDTO) session.getAttribute("logged_user");
        String newPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");
        if (!BCrypt.checkpw(password, user.getPassword())) {
            return getString("error.passwordWrong", session);
        }
        if (!newPassword.equals(confirmPassword)) {
            return getString("error.passwordDoesNotMatch", session);
        }
        ValidatorUtil validatorUtil = new ValidatorUtil();
        validatorUtil.validatePassword(newPassword);

        if (validatorUtil.list.isPresent()) {
            return getString(validatorUtil.list.get().get(0), session);
        }
        user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        userService.update(user);
        request.getSession().setAttribute("message", "label.succesUpdate");
        request.setAttribute("userDTO", user);

        return "controller?action=userRequest";
    }

    private static String getString(String errorMessage, HttpSession session) {
        session.setAttribute("error", errorMessage);
        log.error("error in check password method" + errorMessage);
        return "controller?action=changePassword";
    }
}
