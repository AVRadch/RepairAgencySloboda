package com.my.repairagency007.controller.command.user;

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
import java.util.ArrayList;

import static com.my.repairagency007.controller.command.CommandUtility.moveAttributeFromSessionToRequest;
import static com.my.repairagency007.util.MapperDTOUtil.fillUserDTO;

public class UserProfileCommand  implements Command {


    private static final Logger log = LoggerFactory.getLogger(UserProfileCommand.class);

    private final UserServiceImpl userService;
    /**
     * @param appContext using for get the value of UserServiceImpl instance to use in command
     */
    public UserProfileCommand(AppContext appContext) {userService = appContext.getUserService();}
    /**
     * Method get user from DB and save it in request attribute and return editUser page
     *
     * @param request to get users id for get user from DB and save it in request attribute
     * @return editUser page for redirect
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        log.debug("get request method" + request.getMethod());
        return request.getMethod().equals("POST") ? executePost(request, response) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        moveAttributeFromSessionToRequest(request, "message");
        return "userProfile.jsp";
    }

    private String executePost(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        UserDTO userDTO = (UserDTO) session.getAttribute("logged_user");
        ValidatorUtil validatorUtil = new ValidatorUtil();
        request.setAttribute("email", userDTO.getEmail());
        log.info("Email => " + request.getParameter("email"));
        fillUserDTO(request, userDTO, validatorUtil);

        if (validatorUtil.list.isPresent()){
            ArrayList<String> arrayList = validatorUtil.list.get();
            log.info("List error => " + arrayList);
            session.setAttribute("errorList", validatorUtil.list.get());
            return "modalErrorList.jsp";
        }

        String forward = "controller?action=userRequest";

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
