package com.my.repairagency007.DAO.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JDBCDataSource {

    private static final Logger log = LoggerFactory.getLogger(JDBCDataSource.class);
    private static volatile DataSource dataSource;

    static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (JDBCDataSource.class) {
                if (dataSource == null) {
                    Context initContext = null;
                    try {
                        initContext = new InitialContext();
                        Context envContext = (Context) initContext.lookup("java:/comp/env");
                        dataSource = (DataSource) envContext.lookup("TestDB");
                    } catch (NamingException e) {
                        log.error("Error in getDataSourse method", e);
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return dataSource;
    }
}
