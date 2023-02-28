package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.FeedbackServiceImpl;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;

import static com.my.repairagency007.controller.command.CommandUtility.moveAttributeFromSessionToRequest;

public class DeleteFeedbackCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(DeleteFeedbackCommand.class);

    private final FeedbackServiceImpl feedbackService;
    /**
     * @param appContext using for get the value of RequestServiceImpl instance to use in command
     */
    public DeleteFeedbackCommand(AppContext appContext) {feedbackService = appContext.getFeedbackService();}
    /**
     * Method checks request type and calls required implementation
     *
     * @param httpRequest  to get request type
     * @return path to redirect or forward by front-controller
     * @throws ServiceException to call error page in front-controller
     */
    @Override
    public String execute(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServiceException {
        log.debug("get request method" + httpRequest.getMethod());
        return httpRequest.getMethod().equals("POST") ? executePost(httpRequest) : executeGet(httpRequest);
    }
    /**
     * Method called if doGet request send from front-controller.
     * Obtains required path and transfer attributes from session to request
     *
     * @param request to get message attribute from session and put it in request
     * @return command to controller
     */
    private String executeGet(HttpServletRequest request) {
        log.info("Execute delete feedback Get");
        moveAttributeFromSessionToRequest(request, "message");
        return "controller?action=adminAllFeedbacks";
    }
    /**
     * Method called if doPost request send from front-controller. Tries to delete user from database.
     *
     * @param request to get users id and forms message in case of successful deleting
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest request){

        HttpSession session = request.getSession();
        int feedbackId = Integer.parseInt(request.getParameter("feedback-id"));
        log.debug("Delete feedback with id = " + feedbackId);
        String forward = "controller?action=deleteFeedback";
        try {
            feedbackService.delete(feedbackId);
            session.setAttribute("message", "message.successDelete");
        } catch (ServiceException e) {
            session.setAttribute("errorList", new ArrayList<String>().add("error.errorDelete"));
            forward = "modalErrorList.jsp";
        }
        return forward;
    }
}
