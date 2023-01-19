package com.my.repairagency007.model.DAO.implementations;

import com.my.repairagency007.model.DAO.RequestDAO;
import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.entity.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.repairagency007.model.DAO.implementations.SQLQuery.RequestSQL.*;

public class RequestDAOImpl extends GenericDAO implements RequestDAO {

    private static final Logger log = LoggerFactory.getLogger(RequestDAOImpl.class);

    public int getNumberOfRecords(String filter) throws DAOException {
        int numberOfRecords = 0;
        String query = String.format(GET_NUMBER_OF_REQUEST_RECORDS, filter);
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
    public List<Request> findAll(String query) throws DAOException {

        log.trace("Find all request");
        Connection connection = getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;

        ArrayList<Request> result;

        try {
            begin(connection);
            ps = connection.prepareStatement(SQL_SELECT_ALL_REQUEST+query);
            rs = ps.executeQuery();
            result = extractRequestsFromResultSet(rs);
            commit(connection);
            log.trace("ArrayList of Request created");
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error in find All Request methods", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, rs);
        }

        return result;
    }

    @Override
    public Request getEntityById(int id) throws DAOException {
//        log.debug("Find request by id");
        Connection connection = getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Request request = null;

        try{
            begin(connection);
            ps = connection.prepareStatement(SQL_GET_REQUEST_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()){
                request = extractRequestFromResultSet(rs);
            }
            commit(connection);
            log.trace("Request found");
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error in find request by id methods", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, rs);
        }

        return request;
    }

    @Override
    public boolean delete(Request request) throws DAOException {
        return deleteById(request.getId());
    }

    @Override
    public boolean deleteById(int id) throws DAOException {

        log.debug("Delete request by id");
        Connection connection = getConnection();
        PreparedStatement ps = null;
        boolean result = false;

        try{
            begin(connection);
            ps = connection.prepareStatement(SQL_DELETE_REQUEST_BY_ID);
            ps.setInt(1, id);
            if(ps.executeUpdate() > 0) {result = true;}
            connection.commit();
            log.trace("Request deleted");
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error in delete request by id methods", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, null);
        }
        return result;
    }

    @Override
    public boolean create(Request request) throws DAOException {

        log.debug("Create request");
        Connection connection = getConnection();
        PreparedStatement ps = null;
        boolean result = false;
        ResultSet rs = null;

        try{
            begin(connection);

            ps = connection.prepareStatement(SQL_CREATE_REQUEST, PreparedStatement.RETURN_GENERATED_KEYS);
            fillRequest(ps, request);
            if(ps.executeUpdate() > 0) {result = true;}
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
                request.setId(id);
            }

            connection.commit();
            log.trace("Request created");
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error create request", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, rs);
        }
        return result;
    }

    @Override
    public Request update(Request request) throws DAOException {

        log.debug("Update request");
        Connection connection = getConnection();
        PreparedStatement ps = null;

        try{
            begin(connection);

            ps = connection.prepareStatement(SQL_UPDATE_REQUEST);
            fillRequest(ps, request);
            ps.setInt(6, request.getId());
            ps.executeUpdate();

            connection.commit();
            log.trace("Request updated");
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error update request", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, null);
        }
        return request;
    }

    @Override
    public List<Request> findRequestByCompletionStatus() throws DAOException {

        log.trace("Find requests by completion Status");
        Connection connection = getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;

        ArrayList<Request> result;

        try {
            begin(connection);
            ps = connection.prepareStatement(SQL_SELECT_REQUESTS_BY_COMPLETION_STATUS);
            rs = ps.executeQuery();
            result = extractRequestsFromResultSet(rs);
            commit(connection);
            log.trace("ArrayList of request by completion status created");
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error in find requests by completion status methods", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, rs);
        }
        return result;
    }

    @Override
    public List<Request> findRequestByPaymentStatus() throws DAOException {

        log.trace("Find requests by payment status");
        Connection connection = getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;

        ArrayList<Request> result;

        try {
            begin(connection);
            ps = connection.prepareStatement(SQL_SELECT_REQUESTS_BY_PAYMENT_STATUS);
            rs = ps.executeQuery();
            result = extractRequestsFromResultSet(rs);
            commit(connection);
            log.trace("ArrayList of request by payment status created");
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error in find requests by payment status methods", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, rs);
        }
        return result;
    }

    private ArrayList<Request> extractRequestsFromResultSet(ResultSet rs) throws SQLException {

        ArrayList<Request> requests = new ArrayList<>();

        while (rs.next()) {
            Request request = extractRequestFromResultSet(rs);
            requests.add(request);
        }
        return requests;
    }

    private Request extractRequestFromResultSet(ResultSet rs) throws SQLException  {
        return Request.builder()
                .id(rs.getInt("r_id"))
                .user_id(rs.getInt("users_id"))
                .description(rs.getString("descriptions"))
                .date(rs.getDate("date").toLocalDate())
                .completionStatusId(rs.getInt("completion_status_id"))
                .repairer_id(rs.getInt("repairer_id"))
                .paymentStatusId(rs.getInt("payment_status_id"))
                .totalCost(rs.getInt("total_cost"))
                .build();
    }

    private void fillRequest(PreparedStatement ps, Request request)throws SQLException{

        ps.setString(1, request.getDescription());
        ps.setDate(2, Date.valueOf(request.getDate()));
        ps.setInt(3, request.getCompletionStatusId());
        ps.setInt(4, request.getPaymentStatusId());
        ps.setInt(5, request.getTotalCost());

    }
}
