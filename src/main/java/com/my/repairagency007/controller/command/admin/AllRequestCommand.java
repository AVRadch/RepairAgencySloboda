package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import com.my.repairagency007.util.query.QueryBuilder;
import com.my.repairagency007.util.query.RequestQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.my.repairagency007.model.entity.Role.*;
import static com.my.repairagency007.util.PaginationUtil.paginate;
/**
 * This is AllRequestCommand class. Accessible by admin.
 * Return list of requests based on sorted, filtered and pagination parameters.
 *
 * @author Alex Radchenko
 * @version 1.0
 */

public class AllRequestCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(AllRequestCommand.class);

    private final RequestServiceImpl requestService;

    private final UserServiceImpl userService;
    /**
     * @param appContext using for get the value of FeedbackServiceImpl and RequestServiceImpl
     * instance to use in command
     */
    public AllRequestCommand(AppContext appContext) {
        requestService = appContext.getRequestService();
        userService = appContext.getUserService();
    }
    /**
     * Method execute forms query with sorted, filtered and pagination parameters for
     * getAll method RequestService. After that, the parameters for pagination are calculated
     * and the requests list is filled in and saved in the session. Also method get list of
     * repairs from getAllRepairer method UserService
     *
     * @param request to get parameters for query forms
     * @return view requestsforAdmin page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        HttpSession session = request.getSession();

        String forward = "requestsForAdmin.jsp";
        List<RequestDTO> requests;

        log.info("создание списка реквестов");
        QueryBuilder queryBuilder = getQueryBuilder(request);
        log.info("получена query builder " + queryBuilder);
        requests = requestService.getAll(queryBuilder.getQuery());
        int numberOfRecords = requestService.getNumberOfRecords(queryBuilder.getRecordQuery());
        List<UserDTO> repairers = userService.getAllRepairer();

        paginate(numberOfRecords, request);
        log.info("Pages => " + request.getAttribute("pages"));
        session.setAttribute("pages", request.getAttribute("pages"));

        session.setAttribute("requestDTOS", requests);
        session.setAttribute("repairers", repairers);

        return forward;
    }
/**
 * Method get parameters from request and forms FeedbackQueryBuilder
 *
 * @param request to get parameters for RequestQueryBuilder
 * @return RequestQueryBuilder
 **/
    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        int status = Integer.parseInt(request.getParameter("status-id")) + 1;
        return new RequestQueryBuilder()
                .setCompletionStatusFilter(String.valueOf(status))
                .setDateFilter(request.getParameter("date"))
                .setSortField(request.getParameter("sort"))
                .setOrder(request.getParameter("order"))
                .setLimits(request.getParameter("offset"), request.getParameter("records"));
    }
}
