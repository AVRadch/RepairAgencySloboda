package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import com.my.repairagency007.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.io.IOException;
import java.util.ArrayList;

import static com.my.repairagency007.controller.command.CommandUtility.moveAttributeFromSessionToRequest;
import static com.my.repairagency007.util.MapperDTOUtil.fillUserDTO;

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
        return "editUser.jsp";
    }

    private String executePost(HttpServletRequest request, HttpServletResponse response) {

        int id = Integer.parseInt(request.getParameter("user-id"));
        log.info("Get id user for update " + id);
        HttpSession session = request.getSession();
        ValidatorUtil validatorUtil = new ValidatorUtil();
        UserDTO userDTO = null;
        try {
            userDTO = userService.getById(id);
        } catch (ServiceException e) {
            session.setAttribute("errorList", new ArrayList<String>().add("error.updateDB"));
            return "modalErrorList.jsp";
        }

            fillUserDTO(request, userDTO, validatorUtil);
            userDTO.setRole(request.getParameter("role"));
            userDTO.setAccount(request.getParameter("account").replace(",", "."));
            validatorUtil.validateAccount(userDTO.getAccount());

            if (validatorUtil.list.isPresent()){
                ArrayList<String> arrayList = validatorUtil.list.get();
                log.info("List error => " + arrayList);
                session.setAttribute("errorList", validatorUtil.list.get());
                return "modalErrorList.jsp";
            }

        String forward = "controller?action=adminAllUsers";

        try {
            userService.update(userDTO);
            request.getSession().setAttribute("message", "label.succesUpdate");
            request.setAttribute("userDTO", userDTO);
        } catch (ServiceException e) {
            session.setAttribute("errorList", new ArrayList<String>().add("error.updateDB"));
            forward = "modalErrorList.jsp";
        }
        return forward;
    }
}
