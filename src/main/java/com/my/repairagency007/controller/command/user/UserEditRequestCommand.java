package com.my.repairagency007.controller.command.user;

import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserEditRequestCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(UserEditRequestCommand.class);

    private final RequestServiceImpl requestService;

    private final UserServiceImpl userService;

    public UserEditRequestCommand() {
        requestService = AppContext.getAppContext().getRequestService();
        userService = AppContext.getAppContext().getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        int id = Integer.parseInt(request.getParameter("request-id"));
        RequestDTO requestDTO = requestService.getById(id);
        List<UserDTO> repairers = userService.getAllRepairer();
        request.setAttribute("requestDTO", requestDTO);
        request.setAttribute("repairers", repairers);
        log.debug("Get request" + requestDTO.getDescription());
        log.debug("get request method" + request.getMethod());

        return "userEditRequest.jsp";
    }
}
