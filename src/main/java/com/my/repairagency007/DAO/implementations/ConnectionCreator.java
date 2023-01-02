package com.my.repairagency007.DAO.implementations;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionCreator {

    private final DataSource dataSource = JDBCDataSource.getDataSource();

    private ConnectionCreator() {    }

    public Connection openConnection() throws SQLException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
