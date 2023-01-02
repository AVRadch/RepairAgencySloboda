package com.my.repairagency007.DAO.implementations;

public abstract class SQLQuery {
    static class UserSQL{
        public static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
        public static final String SQL_GET_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
        public static final String SQL_GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
        public static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
        public static final String SQL_CREATE_USER = "INSERT INTO users(notification, phone_number, account, status, password, " +
            "first_name, last_name, email, role_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        public static final String SQL_UPDATE_USER = "UPDATE users SET notification = ?," +
                " phone_number = ?, account = ?, status = ?, password = ?, first_name = ?, " +
                "last_name = ?, email = ?, role_id = ? WHERE id = ?";
    }
    static class RequestSQL {
        public static final String SQL_SELECT_ALL_REQUEST = "SELECT * FROM request";
        public static final String SQL_GET_REQUEST_BY_ID = "SELECT * FROM request WHERE id = ?";
        public static final String SQL_DELETE_REQUEST_BY_ID = "DELETE FROM request WHERE id = ?";
        public static final String SQL_CREATE_REQUEST = "INSERT INTO request(description, date, completion_status, " +
                "payment_status, total_cost) VALUES(?, ?, ?, ?, ?)";
        public static final String SQL_UPDATE_REQUEST = "UPDATE request SET description = ?, date = ?, " +
                "completion_status = ?, payment_status = ?, total_cost = ? WHERE id = ?";
        public static final String SQL_SELECT_REQUESTS_BY_COMPLETION_STATUS = "SELECT * FROM request WHERE completion_status = ?";
        public static final String SQL_SELECT_REQUESTS_BY_PAYMENT_STATUS = "SELECT * FROM request WHERE payment_status = ?";
    }
    static class FeedbackSQL {
        public static final String SQL_SELECT_ALL_FEEDBACKS = "SELECT * FROM feedback";
        public static final String SQL_GET_FEEDBACK_BY_ID = "SELECT * FROM feedback WHERE id = ?";
        public static final String SQL_DELETE_FEEDBACK_BY_ID = "DELETE FROM feedback WHERE id = ?";
        public static final String SQL_CREATE_FEEDBACK = "INSERT INTO feedback(repairer_id, date_time, " +
                "feedback_text, rating, request_id) VALUES(?, ?, ?, ?, ?)";
        public static final String SQL_UPDATE_FEEDBACK = "UPDATE feedback SET repairer_id = ?, date_time = ?, " +
                "feedback_text = ?, rating = ?, request_id = ? WHERE id = ?";

    }
}
