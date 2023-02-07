package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.entity.CompletionStatus;
import com.my.repairagency007.model.entity.PaymentStatus;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.time.LocalDate;

import static com.my.repairagency007.controller.command.CommandUtility.moveAttributeFromSessionToRequest;
import static com.my.repairagency007.util.MapperDTOUtil.fillRequestDTO;

public class UpdateRequestAdminCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(UpdateRequestAdminCommand.class);

    private final RequestServiceImpl requestService;

    private final UserServiceImpl userService;

    public UpdateRequestAdminCommand(AppContext appContext) {
        requestService = appContext.getRequestService();
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        log.debug("get request method" + request.getMethod());
        return request.getMethod().equals("POST") ? executePost(request, response) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        moveAttributeFromSessionToRequest(request, "message");
        moveAttributeFromSessionToRequest(request, "error");
        return "editUser.jsp";
    }

    private String executePost(HttpServletRequest request, HttpServletResponse response) {

        RequestDTO requestDTO = null;
        HttpSession session = request.getSession();
        try {
            requestDTO = requestService.getById(Integer.parseInt(request.getParameter("request-id")));
        } catch (ServiceException e) {
            session.setAttribute("error", "error.errorUpdateRequest");
            return "controller?action=updateRequest";
        }

        fillRequestDTO(request, requestDTO);
        int id = requestDTO.getUser_id();
        log.debug("Get id user for update = " + id);
        UserDTO userDTO = null;
        try {
            userDTO = userService.getById(id);
        } catch (ServiceException e) {
            session.setAttribute("error", "error.errorUpdateRequest");
            return "controller?action=updateRequest";
        }
        log.debug("Get user for update " + userDTO.getLastName());
        id = requestDTO.getRepairer_id();
        log.debug("Get id repairer for update = " + id);
        if(id != 0) {
            try {
                UserDTO repairerDTO = userService.getById(id);
            } catch (ServiceException e) {
                session.setAttribute("error", "error.errorUpdateRequest");
                return "controller?action=updateRequest";
            }
        }
        log.debug("Get repairer for update " + userDTO.getLastName());
        log.debug("Request DTO = " + requestDTO);

        String forward = "controller?action=adminAllRequest";

        try {
            requestService.update(requestDTO);
        } catch (ServiceException e) {
            session.setAttribute("error", "error.errorUpdateRequest");
            return "controller?action=updateRequest";
        }
        if (requestDTO.getRepairer_id() !=0 ){
            try {
                requestService.updateRepairForRequest(requestDTO);
            } catch (ServiceException e) {
                session.setAttribute("error", "error.errorUpdateRequest");
                return "controller?action=updateRequest";
            }
        }

        request.getSession().setAttribute("message", "label.succesUpdate");
        request.setAttribute("requestDTO", requestDTO);

        return forward;
    }
}
