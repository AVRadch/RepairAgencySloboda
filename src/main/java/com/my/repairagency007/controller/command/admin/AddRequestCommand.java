package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.entity.CompletionStatus;
import com.my.repairagency007.model.entity.PaymentStatus;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.time.LocalDate;

import static com.my.repairagency007.controller.command.CommandUtility.moveAttributeFromSessionToRequest;

public class AddRequestCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(AddRequestCommand.class);

    private final RequestServiceImpl requestService;

    public AddRequestCommand() {requestService = AppContext.getAppContext().getRequestService();}


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return request.getMethod().equals("POST") ? executePost(request, response) : executeGet(request);
    }
    private String executeGet(HttpServletRequest request) {
        log.debug("Get method add request");
        moveAttributeFromSessionToRequest(request, "message");
        moveAttributeFromSessionToRequest(request, "error");
        return "requestForUser.jsp";
    }

    private String executePost(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        log.debug("Post method add request");
        HttpSession session = request.getSession();
        UserDTO userDTO = (UserDTO) session.getAttribute("logged_user");
        RequestDTO requestDTO = RequestDTO.builder().build();
        log.debug("Build request DTO");
        requestDTO.setDescription(request.getParameter("description"));
        requestDTO.setUser_id(userDTO.getId());
        requestDTO.setDate(LocalDate.now().toString());
        requestDTO.setCompletionStatus(CompletionStatus.NOT_STARTED.getName());
        requestDTO.setPaymentStatus(PaymentStatus.PANDING_PAYMENT.getName());
        log.debug("fill RequestDTO");

        String forward = "controller?action=userRequest";

        requestService.create(requestDTO);

        String errorMessage;

        return forward;
    }
}
