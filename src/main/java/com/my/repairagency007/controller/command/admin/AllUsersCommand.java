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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.my.repairagency007.model.entity.Role.*;

public class AllUsersCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(AllUsersCommand.class);

    private final UserServiceImpl userService;

    public AllUsersCommand() {
        userService = AppContext.getAppContext().getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        HttpSession session = request.getSession();
        log.debug("Получили сессию");
        RequestDispatcher dispatcher;

        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        log.debug("Получили юзера из сессии");
        List<UserDTO> users;
        log.debug("User role" + currentUser.getRole());

        if (currentUser.getRole().equals(MANAGER.getName())  || currentUser.getRole().equals(CRAFTSMAN.getName())) {
            try {
                log.debug("создание списка юзеров");
                QueryBuilder queryBuilder = getQueryBuilder(request);
                log.debug("Построен Query Builder");
                users = userService.getAll(queryBuilder.getQuery());
                log.debug("Получены usersDTO");
                for (UserDTO user: users
                     ) {
                    log.debug("user: " + user);
                }
                log.debug("залогированы user DTO");
                int numberOfRecords = userService.getNumberOfRecords(queryBuilder.getRecordQuery());
            } catch (ServiceException e) {
                log.error("Не получилось создать список реквестов");
                return "error_page.jsp";
            }
            request.setAttribute("userDTOS", users);

            dispatcher = request.getRequestDispatcher("usersForAdmin.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException e) {
                log.error("Ошибка перехода на страницу со списком запросов", e);
            }
        } else {
            try {
                response.sendRedirect("controller?action=registration");
            } catch (IOException e) {
                log.error("Не получилось перейти на страницу Sign In");
            }
        }
        return "requestsForAdmin.jsp";
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return new UserQueryBuilder()
                .setDateFilter(request.getParameter("date"))
                .setSortField(request.getParameter("sort"))
                .setOrder(request.getParameter("order"))
                .setLimits(request.getParameter("offset"), request.getParameter("records"));
    }
}
