package com.my.repairagency007.model.DAO.implementations;

import com.my.repairagency007.model.DAO.UserDAO;
import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static com.my.repairagency007.model.DAO.implementations.SQLQuery.UserSQL.*;

public class UserDAOImpl extends GenericDAO implements UserDAO {

    private static final Logger log = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public int getNumberOfRecords(String filter) throws DAOException {
        int numberOfRecords = 0;
        String query = String.format(GET_NUMBER_OF_USERS_RECORDS, filter);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    numberOfRecords = resultSet.getInt("numberOfRecords");
                }
            }
        }catch (SQLException e) {
            throw new DAOException(e);
        }
        return numberOfRecords;
    }

    @Override
    public List<User> findAll(String query) throws DAOException {

        log.trace("Find all users");
        Connection connection = getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;

        ArrayList<User> result = null;

        try{
            begin(connection);
            ps = connection.prepareStatement(SQL_SELECT_ALL_USERS + query);
            rs = ps.executeQuery();
            result = extractUsersFromResultSet(rs);
            commit(connection);
            log.debug("ArrayList of Users created" + SQL_SELECT_ALL_USERS + query);
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error in find All Users methods", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, rs);
        }

        return result;
    }

    @Override
    public User getEntityById(int id) throws DAOException {
        Connection connection = getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        User user = null;
        try{
            begin(connection);
            ps = connection.prepareStatement(SQL_GET_USER_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if( rs.next() ) {user = extractUserFromResultSet(rs);}
            commit(connection);
            log.trace("User found and created");
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error in find User by id methods", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, rs);
        }

        return user;
    }

    @Override
    public boolean delete(User user) throws DAOException {
        return deleteById(user.getId());
    }

    @Override
    public boolean deleteById(int id) throws DAOException {

        log.debug("Delete user by id");
        Connection connection = getConnection();
        PreparedStatement ps = null;
        boolean result = false;

        try{
            begin(connection);
            ps = connection.prepareStatement(SQL_DELETE_USER_BY_ID);
            ps.setInt(1, id);
            if(ps.executeUpdate() > 0) {result = true;}
            connection.commit();
            log.trace("User deleted");
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error in delete User by id methods", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, null);
        }
        return result;
    }

    @Override
    public boolean create(User user) throws DAOException {

        log.debug("Create user");
        Connection connection = getConnection();
        PreparedStatement ps = null;
        boolean result = false;
        ResultSet rs = null;

        try{
            begin(connection);

            ps = connection.prepareStatement(SQL_CREATE_USER, PreparedStatement.RETURN_GENERATED_KEYS);
            fillUser(ps, user);
            if(ps.executeUpdate() > 0) {result = true;}
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
                user.setId(id);
            }

            connection.commit();
            log.trace("User created");
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error create User", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, rs);
        }
        return result;
    }

    @Override
    public User update(User user) throws DAOException {

        log.debug("Try to update user");
        Connection connection = getConnection();
        PreparedStatement ps = null;

        try{
            begin(connection);

            ps = connection.prepareStatement(SQL_UPDATE_USER);
            fillUser(ps, user);
            ps.setInt(10, user.getId());
            ps.executeUpdate();

            connection.commit();
            log.trace("User updated");
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error update User", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, null);
        }
        return user;
    }

    private ArrayList<User> extractUsersFromResultSet(ResultSet rs) throws SQLException{

        ArrayList<User> users = new ArrayList<>();

        while (rs.next()) {
            User user = extractUserFromResultSet(rs);
            users.add(user);
        }
        return users;
    }

    private void fillUser(PreparedStatement ps, User user)throws SQLException{
        log.debug("Move user to prepared statement = " + user);
        ps.setString(1, user.getNotification());
        ps.setString(2, user.getPhoneNumber());
        ps.setInt(3, user.getAccount());
        ps.setString(4, user.getStatus());
        ps.setString(5, user.getPassword());
        ps.setString(6, user.getFirstName());
        ps.setString(7, user.getLastName());
        ps.setString(8, user.getEmail());
        ps.setInt(9, user.getRoleId());

    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException  {

        return User.builder()
                .id(rs.getInt("u_id"))
                .phoneNumber(rs.getString("phone_number"))
                .account(rs.getInt("account"))
                .status(rs.getString("status"))
                .password(rs.getString("password"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .email(rs.getString("email"))
                .roleId(rs.getInt("role_id"))
                .build();
    }

    @Override
    public Optional<User> getByEmail(String email) throws DAOException {

        log.debug("Find user by email: " + email);
        Connection connection = getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        User user = null;

        try{
            begin(connection);
            ps = connection.prepareStatement(SQL_GET_USER_BY_EMAIL);
            ps.setString(1, email);
            log.debug("Prepare prepare statement");
            rs = ps.executeQuery();
            log.debug("execute ps and recieve result set");
            if (rs.next()){user = extractUserFromResultSet(rs);}
            log.debug("Recieve user from rs");
            commit(connection);
            log.trace("User found and created");
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error in find User by email methods", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, rs);
        }
        return Optional.ofNullable(user);
    }
}
