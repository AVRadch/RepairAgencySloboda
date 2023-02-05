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

public class AllFeedbacksCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(AllRequestCommand.class);

    private final FeedbackServiceImpl feedbackService;

    public AllFeedbacksCommand(AppContext appContext) {
        feedbackService = appContext.getFeedbackService();
    }

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

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return new FeedbackQueryBuilder()
                .setDateFilter(request.getParameter("date"))
                .setSortField(request.getParameter("sort"))
                .setOrder(request.getParameter("order"))
                .setLimits(request.getParameter("offset"), request.getParameter("records"));
    }
}
