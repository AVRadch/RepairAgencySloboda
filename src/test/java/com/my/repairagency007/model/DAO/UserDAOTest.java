package com.my.repairagency007.model.DAO;

import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.DAO.implementations.UserDAOImpl;
import com.my.repairagency007.model.entity.User;
import com.my.repairagency007.util.query.QueryBuilder;
import com.my.repairagency007.util.query.UserQueryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static com.my.repairagency007.model.DAO.DAOTestEntity.getTestUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class UserDAOTest {

    private static final Logger log = LoggerFactory.getLogger(UserDAOTest.class);

    private final DataSource dataSource = mock(DataSource.class);
    UserDAO userDAO = new UserDAOImpl(dataSource);

    @Test
    void testCreate() throws SQLException {

        try (PreparedStatement ignored = prepareMocksWithId(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(ignored.executeUpdate()).thenReturn(1);
            prepareResultSet(resultSet);
            assertDoesNotThrow(() -> userDAO.create(getTestUser()));
        }
    }

    @Test
    void testSqlExceptionCreate() throws SQLException {

        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.create(getTestUser()));
    }

    @Test
    void testGetById() throws DAOException, SQLException {
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            User resultUser = userDAO.getEntityById(1).orElse(null);
            assertNotNull(resultUser);
            Assertions.assertEquals(getTestUser(), resultUser);
        }
    }

    @Test
    void testDelete() throws SQLException {
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> userDAO.deleteById(1));
        }
    }

    @Test
    void testDeleteWithExeption() throws SQLException {
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DAOException.class, () -> userDAO.deleteById(1));
    }

    @Test
    void testGetAll() throws DAOException, SQLException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            when(request.getParameter("records")).thenReturn("1");
            when(request.getParameter("offset")).thenReturn("0");
            List<User> users = userDAO.findAll(getQueryBuilder(request).getQuery());
            assertEquals(1, users.size());
            assertEquals(getTestUser(), users.get(0));
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

    private PreparedStatement prepareMocksWithId(DataSource dataSource) throws SQLException {
        Connection connection = mock(Connection.class);
        log.info("Connection +> " + connection);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        log.info("Prepared Statement +> " + preparedStatement);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement("INSERT INTO users(notification, phone_number, account, status, password, " +
                "first_name, last_name, email, role_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);

        doNothing().when(preparedStatement).setInt(isA(int.class), isA(int.class));
        doNothing().when(preparedStatement).setLong(isA(int.class), isA(long.class));
        doNothing().when(preparedStatement).setString(isA(int.class), isA(String.class));
        when(preparedStatement.executeUpdate()).thenReturn(1);
        ResultSet rs = mock(ResultSet.class);
        when(preparedStatement.getGeneratedKeys()).thenReturn(rs);
        when(rs.next()).thenReturn(false);
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

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return new UserQueryBuilder()
                .setDateFilter(request.getParameter("date"))
                .setSortField(request.getParameter("sort"))
                .setOrder(request.getParameter("order"))
                .setLimits(request.getParameter("offset"), request.getParameter("records"));
    }
}
