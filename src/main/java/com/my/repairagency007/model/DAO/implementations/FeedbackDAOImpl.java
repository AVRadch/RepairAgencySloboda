package com.my.repairagency007.model.DAO.implementations;

import com.my.repairagency007.model.DAO.FeedbackDAO;
import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.entity.Feedback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.my.repairagency007.model.DAO.implementations.SQLQuery.FeedbackSQL.*;

public class FeedbackDAOImpl extends GenericDAO implements FeedbackDAO {
    private static final Logger log = LoggerFactory.getLogger(FeedbackDAOImpl.class);
    @Override
    public List<Feedback> findAll() throws DAOException {
        log.trace("Find all feedbacks");
        Connection connection = getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;

        ArrayList<Feedback> result;

        try{
            begin(connection);
            ps = connection.prepareStatement(SQL_SELECT_ALL_FEEDBACKS);
            rs = ps.executeQuery();
            result = extractFeedbacksFromResultSet(rs);
            commit(connection);
            log.trace("ArrayList of Feedbacks created");
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
    public Feedback getEntityById(int id) throws DAOException {

        log.debug("Find feedback by id");
        Connection connection = getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Feedback feedback;

        try{
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

        return feedback;
    }

    @Override
    public boolean delete(Feedback feedback) throws DAOException {
        return deleteById(feedback.getId());
    }

    @Override
    public boolean deleteById(int id) throws DAOException {

        log.debug("Delete feedback by id");
        Connection connection = getConnection();
        PreparedStatement ps = null;
        boolean result = false;

        try{
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
        Connection connection = getConnection();
        PreparedStatement ps = null;
        boolean result = false;
        ResultSet rs = null;

        try{
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
        Connection connection = getConnection();
        PreparedStatement ps = null;

        try{
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
        Feedback result = new Feedback();

        result.setId(rs.getInt("r_id"));
        result.setRepairerId(rs.getInt("repairer_id"));
        result.setDate(rs.getDate("date_time"));
        result.setFeedback(rs.getString("feedback_text"));
        result.setRating(rs.getInt("rating"));
        result.setRequestId(rs.getInt("request_id"));

        return result;
    }

    private void fillFeedback(PreparedStatement ps, Feedback feedback)throws SQLException {

        ps.setInt(1, feedback.getRepairerId());
        ps.setDate(2, (Date) feedback.getDate());
        ps.setString(3, feedback.getFeedback());
        ps.setInt(4, feedback.getRating());
        ps.setInt(5, feedback. getRequestId());
    }
}
