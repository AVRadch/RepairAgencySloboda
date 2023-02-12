package com.my.repairagency007.controller.command.craftsman;

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
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.my.repairagency007.controller.command.CommandUtility.moveAttributeFromSessionToRequest;

public class SetCompletedRepairCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(SetCompletedRepairCommand.class);

    private final RequestServiceImpl requestService;

    public SetCompletedRepairCommand(AppContext appContext) {
        requestService = appContext.getRequestService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        log.debug("get request method" + request.getMethod());
        return request.getMethod().equals("POST") ? executePost(request, response) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        moveAttributeFromSessionToRequest(request, "message");
        moveAttributeFromSessionToRequest(request, "error");
        return "controller?action=craftsmanRequest";
    }

    public String executePost(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();

        String forward = "controller?action=craftsmanRequest";

        RequestDTO requestDTO = null;
        try {
            requestDTO = requestService.getById(Integer.parseInt(request.getParameter("request-id")));
            requestService.setCompletedRepair(requestDTO);
            session.setAttribute("message", "label.successSetStatus");
        } catch (ServiceException e) {
            session.setAttribute("error", "error.errorSetStatus");
            return "controller?action=setCompletedRepair";
        }
        return forward;
    }

}
