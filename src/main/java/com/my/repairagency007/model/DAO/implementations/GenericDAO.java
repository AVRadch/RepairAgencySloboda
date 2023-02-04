package com.my.repairagency007.model.DAO.implementations;

import com.my.repairagency007.controller.context.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class GenericDAO {

    private static final Logger log = LoggerFactory.getLogger(GenericDAO.class);
    public GenericDAO() {

    }
 /*   protected Connection getConnection(){

        Connection connection = null;

            try {
                connection = dataSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        return connection;
    }       */
    public void begin(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
    }
    public void commit(Connection connection) throws SQLException {
        connection.commit();
    }
    public void rollback(Connection connection){
        try {
            if (connection != null) {
                connection.rollback();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
                log.trace("Statement closed");
            }
        } catch (SQLException e) {
            log.warn("Cannot close statement", e);
        }
    }
    private void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
                log.trace("Connection closed");
            }
        } catch (SQLException e) {
            log.warn("Cannot close connection", e);
        }
    }
    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                log.trace("ResultSet closed");
            } catch (SQLException ex) {
                log.warn("Cannot close Result Set" + ex.getSQLState(), ex);
            }
        }
    }
    protected void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }
}
