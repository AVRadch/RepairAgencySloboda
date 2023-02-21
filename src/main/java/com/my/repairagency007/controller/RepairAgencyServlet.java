package com.my.repairagency007.controller;

import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.command.CommandFactory;
import com.my.repairagency007.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/controller")
public class RepairAgencyServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(RepairAgencyServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.info("Make DoGet");
        req.getRequestDispatcher(processRequest(req, resp)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        log.info("Make Redirect DoPost");
        resp.sendRedirect(processRequest(req, resp));
    }

    private String processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        CommandFactory commandFactory = CommandFactory.commandFactory();
        log.info("request " + req);
        Command command = commandFactory.getCommand(req);
        log.info("Execute command " + command);
        String page = "error_page.jsp";
        try {
            page = command.execute(req, resp);
        } catch (Exception e) {
            log.error("Error in main servlet", e);
            page = "controller?action=error";
        }
        log.debug("Command " + page);

        return page;
    }
}
