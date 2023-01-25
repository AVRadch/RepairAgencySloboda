package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
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

import static com.my.repairagency007.util.PaginationUtil.paginate;

public class AdminFilteredRepairedUserCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(AdminFilteredRepairedUserCommand.class);

    private final RequestServiceImpl requestService;

    private final UserServiceImpl userService;

    public AdminFilteredRepairedUserCommand() {
        userService = AppContext.getAppContext().getUserService();
        requestService = AppContext.getAppContext().getRequestService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        HttpSession session = request.getSession();

        UserDTO currentUser = (UserDTO) session.getAttribute("logged_user");
        log.debug("Получили юзера из сессии");

        int id = Integer.parseInt(request.getParameter("reparier-id"));
        String forward = "requestsForAdmin.jsp";
        QueryBuilder queryBuilder = getQueryBuilder(request);
        log.debug("получена query builder " + queryBuilder);
        List<RequestDTO> requests;
        requests = requestService.getByReparierId( queryBuilder.getQuery(), id);

        int numberOfRecords = requests.size();
        log.debug("получение списка мастеров");
        List<UserDTO> repairers = userService.getAllRepairer();
        log.debug("repairs = " + repairers);


        log.debug("Paginate list = " + requests);
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
