package com.my.repairagency007.model.DAO.implementations;

import com.my.repairagency007.model.DAO.FeedbackDAO;
import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.entity.Feedback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.my.repairagency007.model.DAO.implementations.SQLQuery.FeedbackSQL.*;

public class FeedbackDAOImpl extends GenericDAO implements FeedbackDAO {

    private final DataSource dataSource;

    public FeedbackDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    private static final Logger log = LoggerFactory.getLogger(FeedbackDAOImpl.class);

    public int getNumberOfRecords(String filter) throws DAOException {
        int numberOfRecords = 0;
        String query = String.format(GET_NUMBER_OF_FEEDBACK_RECORDS, filter);
        try (Connection connection = dataSource.getConnection();
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
    public List<Feedback> findAll(String query) throws DAOException {
        log.trace("Find all feedbacks");
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        ArrayList<Feedback> result;

        try{
            connection = dataSource.getConnection();
            begin(connection);
            ps = connection.prepareStatement(SQL_SELECT_ALL_FEEDBACKS + query);
            rs = ps.executeQuery();
            result = extractFeedbacksFromResultSet(rs);
            commit(connection);
            log.debug("ArrayList of feedbacks created" + SQL_SELECT_ALL_FEEDBACKS + query);
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error in find All feedbacks methods", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, rs);
        }

        return result;
    }

    @Override
    public Optional<Feedback> getEntityById(int id) throws DAOException {

        log.debug("Find feedback by id");
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Feedback feedback;

        try{
            connection = dataSource.getConnection();
            begin(connection);
            ps = connection.prepareStatement(SQL_GET_FEEDBACK_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            feedback = extractFeedbackFromResultSet(rs);
            commit(connection);
            log.trace("Feedback found");
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error in find feedback by id methods", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, rs);
        }

        return Optional.ofNullable(feedback);
    }

    @Override
    public boolean delete(Feedback feedback) throws DAOException {
        return deleteById(feedback.getId());
    }

    @Override
    public boolean deleteById(int id) throws DAOException {

        log.debug("Delete feedback by id");
        Connection connection = null;
        PreparedStatement ps = null;
        boolean result = false;

        try{
            connection = dataSource.getConnection();
            begin(connection);
            ps = connection.prepareStatement(SQL_DELETE_FEEDBACK_BY_ID);
            ps.setInt(1, id);
            if(ps.executeUpdate() > 0) {result = true;}
            connection.commit();
            log.trace("Feedback deleted");
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error in delete feedback by id methods", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, null);
        }
        return result;
    }

    @Override
    public boolean create(Feedback feedback) throws DAOException {

        log.debug("Create feedback");
        Connection connection =  null;
        PreparedStatement ps = null;
        boolean result = false;
        ResultSet rs = null;

        try{
            connection = dataSource.getConnection();
            begin(connection);

            ps = connection.prepareStatement(SQL_CREATE_FEEDBACK, PreparedStatement.RETURN_GENERATED_KEYS);
            fillFeedback(ps, feedback);
            if(ps.executeUpdate() > 0) {result = true;}
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
                feedback.setId(id);
            }

            connection.commit();
            log.trace("Feedback created");
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error create feedback", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, rs);
        }
        return result;
    }

    @Override
    public Feedback update(Feedback feedback) throws DAOException {

        log.debug("Update feedback");
        Connection connection = null;
        PreparedStatement ps = null;

        try{
            connection = dataSource.getConnection();
            begin(connection);

            ps = connection.prepareStatement(SQL_UPDATE_FEEDBACK);
            fillFeedback(ps, feedback);
            ps.setInt(6, feedback.getId());
            ps.executeUpdate();

            connection.commit();
            log.trace("Feedback updated");
        } catch (SQLException e) {
            rollback(connection);
            log.error("Error update feedback", e);
            throw new DAOException(e);
        } finally {
            close(connection, ps, null);
        }
        return feedback;
    }

    private ArrayList<Feedback> extractFeedbacksFromResultSet(ResultSet rs) throws SQLException {

        ArrayList<Feedback> feedbacks = new ArrayList<>();

        while (rs.next()) {
            Feedback feedback = extractFeedbackFromResultSet(rs);
            feedbacks.add(feedback);
        }
        return feedbacks;
    }

    private Feedback extractFeedbackFromResultSet(ResultSet rs) throws SQLException {

        return Feedback.builder()
                .id(rs.getInt("feedback_id"))
 //               .repairerId(rs.getInt("repairer_id"))
                .date(rs.getDate("date_time").toLocalDate())
                .feedback(rs.getString("feedback_text"))
                .rating(rs.getInt("rating"))
                .requestId(rs.getInt("request_id"))
                .build();
    }

    private void fillFeedback(PreparedStatement ps, Feedback feedback)throws SQLException {

        ps.setDate(1, Date.valueOf( feedback.getDate()));
        ps.setString(2, feedback.getFeedback());
        ps.setInt(3, feedback.getRating());
        ps.setInt(4, feedback. getRequestId());
    }
}
