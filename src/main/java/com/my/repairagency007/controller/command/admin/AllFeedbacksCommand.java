package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.DTO.FeedbackDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.FeedbackServiceImpl;
import com.my.repairagency007.util.query.FeedbackQueryBuilder;
import com.my.repairagency007.util.query.QueryBuilder;
import com.my.repairagency007.util.query.RequestQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.my.repairagency007.model.entity.Role.CRAFTSMAN;
import static com.my.repairagency007.model.entity.Role.MANAGER;

public class AllFeedbacksCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(AllRequestCommand.class);

    private final FeedbackServiceImpl feedbackService;

    public AllFeedbacksCommand() {
        feedbackService = AppContext.getAppContext().getFeedbackService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        HttpSession session = request.getSession();
        log.debug("Получили сессию");
        RequestDispatcher dispatcher;

        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        log.debug("Получили юзера из сессии");
        List<FeedbackDTO> feedbackDTOS;

        if (currentUser.getRole().equals(MANAGER.getName()) || currentUser.getRole().equals(CRAFTSMAN.getName()) ) {
            try {
                log.debug("создание списка фидбеков");
                QueryBuilder queryBuilder = getQueryBuilder(request);
                feedbackDTOS = feedbackService.getAll(queryBuilder.getQuery());
                int numberOfRecords = feedbackService.getNumberOfRecords(queryBuilder.getRecordQuery());
            } catch (ServiceException e) {
                log.error("Не получилось создать список фидбеков");
                return "error_page.jsp";
            }
            request.setAttribute("feedbackDTOS", feedbackDTOS);

            dispatcher = request.getRequestDispatcher("feedbacksForAdmin.jsp");
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
        return null;
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return new FeedbackQueryBuilder()
                .setDateFilter(request.getParameter("date"))
                .setSortField(request.getParameter("sort"))
                .setOrder(request.getParameter("order"))
                .setLimits(request.getParameter("offset"), request.getParameter("records"));
    }
}
