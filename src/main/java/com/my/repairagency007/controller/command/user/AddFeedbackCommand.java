package com.my.repairagency007.controller.command.user;

import com.my.repairagency007.DTO.FeedbackDTO;
import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.entity.CompletionStatus;
import com.my.repairagency007.model.entity.PaymentStatus;
import com.my.repairagency007.model.services.impl.FeedbackServiceImpl;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

import static com.my.repairagency007.util.MapperDTOUtil.fillFeedbackDTO;

public class AddFeedbackCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(AddFeedbackCommand.class);

    private final FeedbackServiceImpl feedbackService;

    private final RequestServiceImpl requestService;

    private final UserServiceImpl userService;

    public AddFeedbackCommand() {
        feedbackService = AppContext.getAppContext().getFeedbackService();
        requestService = AppContext.getAppContext().getRequestService();
        userService = AppContext.getAppContext().getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        log.debug("Post method add feedback");
        int requestId = Integer.parseInt(request.getParameter("request-id"));
        log.debug("request id = " + requestId);
        HttpSession session = request.getSession();
        log.debug("Build feedback DTO");
        session.setAttribute("request-id", requestId);
        log.debug("move requestId to session");

        String forward = "addFeedback.jsp";

        return forward;
    }


}
