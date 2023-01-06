package com.my.repairagency007.controller.context;

import com.my.repairagency007.model.DAO.implementations.RequestDAOImpl;
import com.my.repairagency007.model.DAO.implementations.UserDAOImpl;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import jakarta.servlet.ServletContext;
import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;

public class AppContext {

    private static AppContext appContext;

    @Setter
    @Getter
    private DataSource dataSource = JDBCDataSource.getDataSource();

    @Getter private final RequestServiceImpl requestService = new RequestServiceImpl(new RequestDAOImpl(), new UserDAOImpl());
    @Getter private final UserServiceImpl userService = new UserServiceImpl(new UserDAOImpl());

    @Getter
    private final ServletContext servletContext;

    public AppContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public static AppContext getAppContext() {
        return appContext;
    }

    public static void createAppContext(ServletContext servletContext) {
        appContext = new AppContext(servletContext);
    }
}
