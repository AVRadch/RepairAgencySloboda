package com.my.repairagency007.controller.context;

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

import javax.servlet.ServletContext;
import javax.sql.DataSource;

public class AppContext {

    @Getter private static AppContext appContext;

    @Getter private static final DataSource dataSource = JDBCDataSource.getDataSource();

    @Getter private final UserDAO userDAO = new UserDAOImpl();

    @Getter private final UserServiceImpl userService = new UserServiceImpl(getUserDAO());

    @Getter private final RequestDAO requestDAO = new RequestDAOImpl();

    @Getter private final RequestServiceImpl requestService = new RequestServiceImpl(getRequestDAO(), getUserDAO());

    @Getter private final FeedbackDAO feedbackDAO = new FeedbackDAOImpl();

    @Getter private final FeedbackServiceImpl feedbackService = new FeedbackServiceImpl(getFeedbackDAO());

    @Getter private final PDFUtil pdfUtil;

    public AppContext(ServletContext servletContext) {

        pdfUtil = new PDFUtil(servletContext);
    }

    public static void createAppContext(ServletContext servletContext) {
        appContext = new AppContext(servletContext);
    }
}
