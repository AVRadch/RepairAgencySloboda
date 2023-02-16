package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.DTO.FeedbackDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.FeedbackServiceImpl;
import com.my.repairagency007.util.query.FeedbackQueryBuilder;
import com.my.repairagency007.util.query.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.my.repairagency007.model.entity.Role.CRAFTSMAN;
import static com.my.repairagency007.model.entity.Role.MANAGER;
import static com.my.repairagency007.util.PaginationUtil.paginate;
/**
 * This is AllFeedbacksCommand class. Accessible by admin.
 * Return list of feedbacks based on sorted, filtered and pagination parameters.
 *
 * @author Alex Radchenko
 * @version 1.0
 */

public class AllFeedbacksCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(AllRequestCommand.class);

    private final FeedbackServiceImpl feedbackService;
    /**
     * @param appContext using for get the value of FeedbackServiceImpl instance to use in command
     */
    public AllFeedbacksCommand(AppContext appContext) {
        feedbackService = appContext.getFeedbackService();
    }

    /**
     * Method execute forms query with sorted, filtered and pagination parameters for
     * getAll method FeedbackService. After that, the parameters for pagination are calculated
     * and the feedback list is filled in and saved in the session.
     *
     * @param request to get parameters for query forms
     * @return view feedbacksforAdmin page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        HttpSession session = request.getSession();
        String forward = "feedbacksForAdmin.jsp";
        List<FeedbackDTO> feedbackDTOS;

        log.debug("создание списка фидбеков");
        QueryBuilder queryBuilder = getQueryBuilder(request);
        feedbackDTOS = feedbackService.getAll(queryBuilder.getQuery());
        int numberOfRecords = feedbackService.getNumberOfRecords(queryBuilder.getRecordQuery());

        session.setAttribute("feedbackDTOS", feedbackDTOS);
        paginate(numberOfRecords, request);

        return forward;
    }
/**
 * Method get parameters from request and forms FeedbackQueryBuilder
 *
 * @param request to get parameters for FeedbackQueryBuilder
 * @return FeedbackQueryBuilder
 */
 private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return new FeedbackQueryBuilder()
                .setDateFilter(request.getParameter("date"))
                .setSortField(request.getParameter("sort"))
                .setOrder(request.getParameter("order"))
                .setLimits(request.getParameter("offset"), request.getParameter("records"));
    }
}
