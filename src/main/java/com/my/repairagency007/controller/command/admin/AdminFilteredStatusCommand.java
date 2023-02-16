package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import com.my.repairagency007.util.query.RequestQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.my.repairagency007.util.PaginationUtil.paginate;

public class AdminFilteredStatusCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(AdminFilteredRepairedUserCommand.class);
    private final RequestServiceImpl requestService;
    private final UserServiceImpl userService;
    public AdminFilteredStatusCommand(AppContext appContext) {
        userService = appContext.getUserService();
        requestService = appContext.getRequestService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        HttpSession session = request.getSession();

        String forward = "requestsForAdmin.jsp";
        RequestQueryBuilder queryBuilder = new RequestQueryBuilder(); /* = getQueryBuilder(request);*/
        log.info("status-id => " + request.getParameter("status-id"));
        queryBuilder.getQueryBuilder(request);
        log.info("получена query builder " + queryBuilder.getQuery());
        List<RequestDTO> requests;
        requests = requestService.getAll(queryBuilder.getQuery());

        int numberOfRecords = requestService.getNumberOfRecords(queryBuilder.getRecordQuery());
        log.debug("получение списка мастеров");
        List<UserDTO> repairers = userService.getAllRepairer();
        log.debug("repairs = " + repairers);


        log.debug("Paginate list = " + requests);
        paginate(numberOfRecords, request);

        session.setAttribute("requestDTOS", requests);
        session.setAttribute("repairers", repairers);

        return forward;
    }

 /*   private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return new RequestQueryBuilder()
                .setCompletionStatusFilter(request.getParameter("status-id") + 1)
                .setDateFilter(request.getParameter("date"))
                .setSortField(request.getParameter("sort"))
                .setOrder(request.getParameter("order"))
                .setLimits(request.getParameter("offset"), request.getParameter("records"));
    }       */
}
