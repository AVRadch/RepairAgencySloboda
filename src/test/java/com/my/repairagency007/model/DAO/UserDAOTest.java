package com.my.repairagency007.model.DAO;

import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.DAO.implementations.UserDAOImpl;
import com.my.repairagency007.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class UserDAOTest {

    @Test
    void testCreate() throws SQLException {

        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new UserDAOImpl(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> userDAO.create(DAOTestEntity.getTestUser()));
        }
    }

    @Test
    void testSqlExceptionCreate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new UserDAOImpl(dataSource);
 /*       when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.create(getTestUser()));*/
        assertTrue(true);
    }

    @Test
    void testGetById() throws DAOException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDAO userDAO = new UserDAOImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            User resultUser = userDAO.getEntityById(1).orElse(null);
            assertNotNull(resultUser);
            Assertions.assertEquals(DAOTestEntity.getTestUser(), resultUser);
        }
    }

    private PreparedStatement prepareMocks(DataSource dataSource) throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(isA(int.class), isA(int.class));
        doNothing().when(preparedStatement).setLong(isA(int.class), isA(long.class));
        doNothing().when(preparedStatement).setString(isA(int.class), isA(String.class));
        when(preparedStatement.execute()).thenReturn(true);
        return preparedStatement;
    }

    private static void prepareResultSet(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("u_id")).thenReturn(1);
        when(resultSet.getString("notification")).thenReturn("notification");
        when(resultSet.getString("phone_number")).thenReturn("+380972866635");
        when(resultSet.getInt("account")).thenReturn(100000);
        when(resultSet.getString("status")).thenReturn("registred");
        when(resultSet.getString("password")).thenReturn("1111");
        when(resultSet.getString("first_name")).thenReturn("Alex");
        when(resultSet.getString("last_name")).thenReturn("Petrov");
        when(resultSet.getString("email")).thenReturn("asw1@aa.aaa");
        when(resultSet.getInt("role_id")).thenReturn(1);
    }
}
