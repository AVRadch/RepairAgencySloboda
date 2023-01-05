package com.my.repairagency007.controller;

import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.command.CommandFactory;
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
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("Make DoPost");
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        CommandFactory commandFactory = CommandFactory.commandFactory();
        Command command = commandFactory.getCommand(req);
        String page = command.execute(req, resp);
        PrintWriter out = resp.getWriter();
        out.println("Command "+ page);
        log.debug("Command " + page);
        RequestDispatcher dispatcher = req.getRequestDispatcher(page);
        if (!page.equals("redirect")) {
            dispatcher.forward(req, resp);
        }
    }
}
