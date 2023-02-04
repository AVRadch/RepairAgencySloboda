package com.my.repairagency007.controller.context;

import com.my.repairagency007.controller.command.CommandFactory;
import com.my.repairagency007.model.DAO.FeedbackDAO;
import com.my.repairagency007.model.DAO.RequestDAO;
import com.my.repairagency007.model.DAO.UserDAO;
import com.my.repairagency007.model.DAO.implementations.FeedbackDAOImpl;
import com.my.repairagency007.model.DAO.implementations.RequestDAOImpl;
import com.my.repairagency007.model.DAO.implementations.UserDAOImpl;

import com.my.repairagency007.model.services.impl.FeedbackServiceImpl;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import com.my.repairagency007.util.PDFUtil;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

public class AppContext {

    private static final Logger log = LoggerFactory.getLogger(CommandFactory.class);

    @Getter private static AppContext appContext;

    @Getter private final UserDAO userDAO;

    @Getter private final UserServiceImpl userService;

    @Getter private final RequestDAO requestDAO;

    @Getter private final RequestServiceImpl requestService;

    @Getter private final FeedbackDAO feedbackDAO;

    @Getter private final FeedbackServiceImpl feedbackService;

    @Getter private final PDFUtil pdfUtil;

    public AppContext(ServletContext servletContext) {

        DataSource dataSource = JDBCDataSource.getDataSource();
        userDAO = new UserDAOImpl(dataSource);
        requestDAO = new RequestDAOImpl(dataSource);
        feedbackDAO = new FeedbackDAOImpl(dataSource);
        userService = new UserServiceImpl(userDAO);
        requestService = new RequestServiceImpl(requestDAO, userDAO);
        feedbackService = new FeedbackServiceImpl(feedbackDAO, requestService);
        pdfUtil = new PDFUtil(servletContext);
        log.debug("Construct all DAO, data source");
    }

    public static void createAppContext(ServletContext servletContext) {
        log.debug("Create AppContext");
        appContext = new AppContext(servletContext);
    }
}
