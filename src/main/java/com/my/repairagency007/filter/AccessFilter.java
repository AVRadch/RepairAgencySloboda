package com.my.repairagency007.filter;

import com.my.repairagency007.model.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

//@WebFilter(urlPatterns = "/*")
public class AccessFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(AccessFilter.class);
    private final static Map<String, List<String>> commandAccessMap = new HashMap<>();
    private final static Map<String, List<String>> pagesAccessMap = new HashMap<>();
    private final static List<String> commonsCommands = Arrays.asList("login", "logout", "error");
    private final static List<String> commonsPages = Arrays.asList("login.jsp", "registration.jsp",
            "error_page.jsp", "index.jsp", "assets/css/main.css", "css/bootstrap.css", "assets/js/bootstrap.js",
            "registration.css", "css/bootstrap.min.css", "assets/js/bootstrap.min.js", "login.css",
            "modalErrorList.jsp", "css/bootstrap-icons.css", "css/my.css", "css/fonts/bootstrap-icons.woff2",
            "css/fonts/bootstrap-icons.woff");

    public AccessFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        List<String> managerCommandsList = Arrays.asList("registration", "adminAllRequest", "adminAllUsers",
                "adminAllFeedbacks", "adminFilteredRepairerUsers", "adminFilteredStatus", "deleteRequest",
                "deleteUser", "editUser", "updateUser", "updateRequest", "addUser", "editRequest",
                "registrationAdmin");
        List<String> managerPagesList = Arrays.asList("addRequest.jsp", "addRequest.css", "adminAddUser.jsp",
                "changePassword.jsp", "editRequest.jsp", "editUser.jsp", "feedbacksForAdmin.jsp",
                "error_page.jsp", "editUser.jsp", "editRequest.jsp", "deleteUserModal.jsp", "changePassword.jsp",
                "adminAddUser.jsp", "addRequest.jsp", "addFeedback.jsp", "modalErrorList.jsp");
        List<String> craftsmanCommandsList = Arrays.asList("craftsmanRequest", "craftsmanFeedbacks", "setStartRepair",
                "setCompletedRepair");
        List<String> craftsmanPagesList = Arrays.asList("requestsForCraftsman.jsp", "headerCraftsman.jsp",
                "feedbacksForCraftsman.jsp");
        List<String> userCommandsList = Arrays.asList("userRequest", "userEditRequest", "userUpdateRequest",
                "userFeedbacks", "addFeedback", "createFeedback");
        List<String> userPagesList = Arrays.asList("addFeedback.jsp", "addRequest.jsp", "editRequest.jsp", "userEditRequest.jsp",
                "feedbacksForUser.jsp", "requestsForUser.jsp", "requestsForUser.jsp");
        commandAccessMap.put(Role.MANAGER.getName(), managerCommandsList);
        pagesAccessMap.put(Role.MANAGER.getName(), managerPagesList);
        commandAccessMap.put(Role.CRAFTSMAN.getName(), craftsmanCommandsList);
        pagesAccessMap.put(Role.CRAFTSMAN.getName(), craftsmanPagesList);
        commandAccessMap.put(Role.USER.getName(), userCommandsList);
        pagesAccessMap.put(Role.USER.getName(), userPagesList);
        log.info("Access filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String servletPath = httpRequest.getServletPath();
        log.info("pages => " + servletPath.substring(1));
        if (accessAllowedCommand(servletRequest) || accessAllowedPages(servletRequest)) {
            log.info("Filter allow access");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String errorMessages = "error.accessDenied";
                    //"You do not have permission to access the requested resource";
            servletRequest.setAttribute("errorMessage", errorMessages);
            log.info("Filter denied access");
            servletRequest.getRequestDispatcher("error_page.jsp").forward(servletRequest, servletResponse);
        }
    }

    private boolean accessAllowedCommand(ServletRequest request) {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String command = request.getParameter("action");
        log.info("Filter command => " + command);
        if (command == null || command.isEmpty()) {
            return false;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return false;
        }
        log.info("User => " + session.getAttribute("logged_user"));
        log.info("User role => " + session.getAttribute("logged_user_role"));
        Object loggedUserRole = session.getAttribute("logged_user_role");
        if (loggedUserRole == null) {
            return commonsCommands.contains(command);
        }
        String userRole = (String) session.getAttribute("logged_user_role");

        return commandAccessMap.get(userRole).contains(command) || commonsCommands.contains(command);
    }

    private boolean accessAllowedPages(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String servletPath = httpRequest.getServletPath();
        HttpSession session = httpRequest.getSession(false);
        if (session == null){
            log.info("Session == null. Default pages");
            return commonsPages.contains(servletPath.substring(1));
        }

        Object loggedUserRole = session.getAttribute("logged_user_role");
        if (loggedUserRole == null) {
            log.info("Default pages");
            return commonsPages.contains(servletPath.substring(1));
        }
        String userRole = (String) session.getAttribute("logged_user_role");

        log.info(" user role => " + userRole);

        if (servletPath != null) {
            log.info("Select role for servlet Path => " + servletPath.substring(1));
            return pagesAccessMap.get(userRole).contains(servletPath.substring(1)) ||
                    commonsPages.contains(servletPath.substring(1));
        }
        log.info("Servlet path is null!!!");
        return commonsPages.contains(servletPath.substring(1));
    }
}
