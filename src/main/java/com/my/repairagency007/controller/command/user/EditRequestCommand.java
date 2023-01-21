package com.my.repairagency007.controller.command.user;

import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditRequestCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(EditRequestCommand.class);

    private final RequestServiceImpl requestService;

    public EditRequestCommand() {requestService = AppContext.getAppContext().getRequestService();}

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        int id = Integer.parseInt(request.getParameter("request-id"));
        RequestDTO requestDTO = requestService.getById(id);
        request.setAttribute("requestDTO", requestDTO);
        log.debug("Get request" + requestDTO.getDescription());
        log.debug("get request method" + request.getMethod());

        return "editRequest.jsp";
    }
}
