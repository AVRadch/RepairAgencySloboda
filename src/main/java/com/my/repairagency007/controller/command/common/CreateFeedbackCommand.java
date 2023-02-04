package com.my.repairagency007.controller.command.common;

import com.my.repairagency007.DTO.FeedbackDTO;
import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.command.admin.AddRequestCommand;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.FeedbackService;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.my.repairagency007.util.MapperDTOUtil.fillFeedbackDTO;

public class CreateFeedbackCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(CreateFeedbackCommand.class);

    private final RequestServiceImpl requestService;

    private final FeedbackService feedbackService;

    public CreateFeedbackCommand(AppContext appContext) {
        requestService = appContext.getRequestService();
        feedbackService = appContext.getFeedbackService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        HttpSession session = request.getSession();
        FeedbackDTO feedbackDTO = FeedbackDTO.builder().build();
        String requestIdString = session.getAttribute("request-id").toString();
        log.debug("Session attr request-id = " + requestIdString);
        int requestId = Integer.parseInt(requestIdString);
        log.debug("Request id = " + requestId);
        feedbackDTO.setRequestId(requestId);
        log.debug("Get request DTO");
        RequestDTO requestDTO = requestService.getById(requestId);
        log.debug("Fill feedbackDTO");
        fillFeedbackDTO(request, requestDTO, feedbackDTO);

        String forward = "controller?action=userFeedbacks";
        feedbackService.create(feedbackDTO);
        request.getSession().removeAttribute("request-id");
        return forward;
    }
}
