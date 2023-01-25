package com.my.repairagency007.model.DAO.implementations;

import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.DAO.RequestDAO;
import com.my.repairagency007.model.entity.PaymentStatus;
import com.my.repairagency007.model.entity.Request;
import com.my.repairagency007.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.repairagency007.model.DAO.implementations.SQLQuery.RequestSQL.*;
import static com.my.repairagency007.model.DAO.implementations.SQLQuery.UserSQL.SQL_UPDATE_USER_ACCOUNT;

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

    public int getNumberOfUserRecords(String filter, int userId) throws DAOException {
        int numberOfRecords = 0;
        String query = String.format(GET_NUMBER_OF_USER_REQUEST_RECORDS, filter);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
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

    public List<Request> findAllForCraftsman(String query, int userId) throws DAOException {

        log.trace("Find all for user request");
        Connection connection = getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;

        ArrayList<Request> requests;

        try {
            begin(connection);
            ps = connection.prepareStatement(SQL_SELECT_ALL_CRAFTSMAN_REQUEST + query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            requests = extractRequestsFromResultSet(rs);
            commit(connection);
            log.trace("ArrayList of Request created");
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error in find All Request methods", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, rs);
        }

        return requests;
    }

    @Override
    public List<Request> findAllForUser(String query, int userId) throws DAOException {

        log.trace("Find all for user request");
        Connection connection = getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;

        ArrayList<Request> result;

        try {
            begin(connection);
            ps = connection.prepareStatement(SQL_SELECT_ALL_USER_REQUEST + query);
            ps.setInt(1, userId);
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
    public List<Request> findAll(String query) throws DAOException {

        log.trace("Find all request");
        Connection connection = getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;

        ArrayList<Request> result;

        try {
            begin(connection);
            ps = connection.prepareStatement(SQL_SELECT_ALL_REQUEST + query);
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
        boolean result1 = false;

        try{
            begin(connection);

            ps = connection.prepareStatement(SQL_DELETE_FEEDBACKS_REQUEST_BY_ID);
            ps.setInt(1, id);
            if(ps.executeUpdate() > 0) {result1 = true;}
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
        return result&&result1;
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

    public void setStartRepair(Request request) throws DAOException {

        log.debug("Update request = " + request);
        Connection connection = getConnection();
        PreparedStatement ps = null;

        try{
            begin(connection);

            ps = connection.prepareStatement(SQL_UPDATE_REQUEST_SET_START);
            log.debug("try to update request. Query = " + SQL_UPDATE_REQUEST_SET_START);

            ps.setInt(1, request.getRepairer_id());
            ps.setInt(2, request.getCompletionStatusId());
            ps.setInt(3, request.getId());

            log.debug("Fill statement = " + ps);
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
    }

    @Override
    public Request update(Request request) throws DAOException {

        log.debug("Update request = " + request);
        Connection connection = getConnection();
        PreparedStatement ps = null;

        try{
            begin(connection);

            ps = connection.prepareStatement(SQL_UPDATE_REQUEST);
            log.debug("try to update request. Query = " + SQL_UPDATE_REQUEST);
            fillRequestUpdate(ps, request);
            ps.setInt(4, request.getId());
            log.debug("Fill statement = " + ps);
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

    public boolean setPaymentStatusPaid(Request request, User user) throws DAOException {

        log.debug("Start transaction with set payment status");
        boolean result = false;

        if (user.getAccount() > request.getTotalCost()){
            Connection connection = getConnection();
            PreparedStatement ps = null;
            PreparedStatement ps1 = null;
            try {
                begin(connection);
                int new_account = user.getAccount() - request.getTotalCost();
                ps = connection.prepareStatement(SQL_UPDATE_USER_ACCOUNT);
                ps.setInt(1, new_account);
                ps.setInt(2, user.getId());
                ps.executeUpdate();

                ps1 = connection.prepareStatement(SQL_UPDATE_REQUEST_PAYMENTSTATUS);
                log.debug("Set Payment Status = " + PaymentStatus.PAID.ordinal());
                ps1.setInt(1, PaymentStatus.PAID.ordinal() + 1);
                ps1.setInt(2, request.getId());
                ps1.executeUpdate();

                connection.commit();
                log.trace("Payment status PAID");
                result = true;
            } catch (SQLException e) {
                rollback(connection);
                log.error("Error update payment status to paid", e);
                throw new DAOException(e);
            } finally {
                close(null, ps1, null);
                close(connection, ps, null);
            }
        }
        return result;

    }

    public void setPaymentStatusCanceled(Request request) throws DAOException {

        Connection connection = getConnection();
        PreparedStatement ps = null;

        try {
            begin(connection);
            ps = connection.prepareStatement(SQL_UPDATE_REQUEST_PAYMENTSTATUS);
            int newPaymentStatus = PaymentStatus.CANCELED.ordinal() + 1;
            log.debug("Set Payment Status = " + newPaymentStatus);
            log.debug("SQL for Payment Status Canceled" + SQL_UPDATE_REQUEST_PAYMENTSTATUS);
            log.debug("set request id = " + request.getId());

            ps.setInt(1, newPaymentStatus);
            ps.setInt(2, request.getId());
            ps.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error update payment status to canceled", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, null);
        }
    }

    public void updateRepairForRequest(Request request) throws DAOException{

        log.debug("Update request = " + request);
        Connection connection = getConnection();
        PreparedStatement ps = null;

        try{
            begin(connection);

            ps = connection.prepareStatement(SQL_UPDATE_REPAIRER_FOR_REQUEST);
            log.debug("try to update request. Query = " + SQL_UPDATE_REPAIRER_FOR_REQUEST);
            ps.setInt(1, request.getRepairer_id());
            ps.setInt(2, request.getId());
            log.debug("Fill statement = " + ps);
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

        ps.setInt(1, request.getUser_id());
        ps.setString(2, request.getDescription());
        ps.setDate(3, Date.valueOf(request.getDate()));
        ps.setInt(4, request.getCompletionStatusId());
        ps.setInt(5, request.getPaymentStatusId());
        ps.setInt(6, request.getTotalCost());
    }

    private void fillRequestUpdate(PreparedStatement ps, Request request)throws SQLException{
        ps.setInt(1, request.getCompletionStatusId());
        ps.setInt(2, request.getPaymentStatusId());
        ps.setInt(3, request.getTotalCost());
    }
}
