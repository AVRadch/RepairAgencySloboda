package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.my.repairagency007.controller.command.CommandUtility.moveAttributeFromSessionToRequest;

public class DeleteRequestCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(DeleteUserCommand.class);

    private final RequestServiceImpl requestService;

    public DeleteRequestCommand(AppContext appContext) {requestService = appContext.getRequestService();}

    @Override
    public String execute(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServiceException {
            log.debug("get request method" + httpRequest.getMethod());
            return httpRequest.getMethod().equals("POST") ? executePost(httpRequest) : executeGet(httpRequest);
        }

    private String executeGet(HttpServletRequest request) {
        log.info("Execute delete request Get");
        moveAttributeFromSessionToRequest(request, "message");
        moveAttributeFromSessionToRequest(request, "error");
        log.info("request error = " + request.getAttribute("error"));
        return "controller?action=adminAllRequest";
    }

    private String executePost(HttpServletRequest request){

        HttpSession session = request.getSession();
        int requestId = Integer.parseInt(request.getParameter("request-id"));
        log.debug("Delete user with id = " + requestId);
        String forward = "controller?action=adminAllRequest";
        try {
            requestService.delete(requestId);
            session.setAttribute("message", "message.successDelete");
        } catch (ServiceException e) {
            session.setAttribute("error", "error.errorDelete");
        }

        return forward;
    }
}
