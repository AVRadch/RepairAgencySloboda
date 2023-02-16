package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import com.my.repairagency007.util.query.QueryBuilder;
import com.my.repairagency007.util.query.UserQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.my.repairagency007.model.entity.Role.*;
import static com.my.repairagency007.util.PaginationUtil.paginate;
/**
 * This is AllUsersCommand class. Accessible by admin.
 * Return list of users based on sorted, filtered and pagination parameters.
 *
 * @author Alex Radchenko
 * @version 1.0
 */
public class AllUsersCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(AllUsersCommand.class);

    private final UserServiceImpl userService;
    /**
     * @param appContext using for get the value of UserServiceImpl instance to use in command
     */
    public AllUsersCommand(AppContext appContext) {
        userService = appContext.getUserService();
    }
    /**
     * Method execute forms query with sorted, filtered and pagination parameters for
     * getAll method UserService. After that, the parameters for pagination are calculated
     * and the users list is filled in and saved in the session.
     *
     * @param request to get parameters for query forms
     * @return view usersforAdmin page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        HttpSession session = request.getSession();

        List<UserDTO> users;
        String forward = "usersForAdmin.jsp";

        log.debug("создание списка юзеров");
        QueryBuilder queryBuilder = getQueryBuilder(request);
        log.debug("Построен Query Builder" + queryBuilder.getQuery());
        users = userService.getAll(queryBuilder.getQuery());
        log.debug("Получены usersDTO" + queryBuilder.getQuery());
        log.debug("залогированы user DTO");
        int numberOfRecords = userService.getNumberOfRecords(queryBuilder.getRecordQuery());

        session.setAttribute("userDTOS", users);
        paginate(numberOfRecords, request);

        return forward;
    }
    /**
     * Method get parameters from request and forms UserQueryBuilder
     *
     * @param request to get parameters for UserQueryBuilder
     * @return UserQueryBuilder
     */
    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return new UserQueryBuilder()
                .setDateFilter(request.getParameter("date"))
                .setSortField(request.getParameter("sort"))
                .setOrder(request.getParameter("order"))
                .setLimits(request.getParameter("offset"), request.getParameter("records"));
    }
}
