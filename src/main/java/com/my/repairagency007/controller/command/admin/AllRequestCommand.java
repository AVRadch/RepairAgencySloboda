package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.DAO.implementations.RequestDAOImpl;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.command.common.LoginCommand;
import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.entity.Request;
import com.my.repairagency007.model.entity.Role;
import com.my.repairagency007.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.my.repairagency007.model.entity.Role.*;

public class AllRequestCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(AllRequestCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        log.debug("Получили сессию");
        RequestDispatcher dispatcher;

        User currentUser = (User) session.getAttribute("user");
        log.debug("Получили юзера из сессии");
        List<Request> requests;
        log.debug("User role" + getRole(currentUser));
        if (getRole(currentUser) == USER || getRole(currentUser) == CRAFTSMAN) {
            try {
                log.debug("создание списка реквестов");
                requests = new RequestDAOImpl().findAll();
            } catch (DAOException e) {
                log.error("Не получилось создать список реквестов");
                return "error_page.jsp";
            }
            request.setAttribute("requests", requests);

            dispatcher = request.getRequestDispatcher("requestsForAdmin.jsp");
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
        return "error_page.jsp";
    }
}
