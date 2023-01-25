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

public class AllRequestCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(AllRequestCommand.class);

    private final RequestServiceImpl requestService;

    private final UserServiceImpl userService;

    public AllRequestCommand() {
        requestService = AppContext.getAppContext().getRequestService();
        userService = AppContext.getAppContext().getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        HttpSession session = request.getSession();

        UserDTO currentUser = (UserDTO) session.getAttribute("logged_user");
        log.debug("Получили юзера из сессии");
        String forward = "requestsForAdmin.jsp";
        List<RequestDTO> requests;

        log.debug("создание списка реквестов");
        QueryBuilder queryBuilder = getQueryBuilder(request);
        log.debug("получена query builder " + queryBuilder);
        requests = requestService.getAll(queryBuilder.getQuery());
        int numberOfRecords = requestService.getNumberOfRecords(queryBuilder.getRecordQuery());
        List<UserDTO> repairers = userService.getAllRepairer();

        paginate(numberOfRecords, request);
        session.setAttribute("requestDTOS", requests);
        session.setAttribute("repairers", repairers);

        return forward;
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return new RequestQueryBuilder()
                .setDateFilter(request.getParameter("date"))
                .setSortField(request.getParameter("sort"))
                .setOrder(request.getParameter("order"))
                .setLimits(request.getParameter("offset"), request.getParameter("records"));
    }
}
