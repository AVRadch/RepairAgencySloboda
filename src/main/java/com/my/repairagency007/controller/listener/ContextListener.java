package com.my.repairagency007.controller.listener;

import com.my.repairagency007.controller.context.AppContext;
import jakarta.servlet.ServletContextEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(ContextListener.class);

    public void contextInitialized(ServletContextEvent sce) {
        log.info("AppContext initialized");
        AppContext.createAppContext(sce.getServletContext());
    }
}
