package com.my.repairagency007.model.DAO.implementations;

public abstract class SQLQuery {
    static class UserSQL{
        public static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
        public static final String SQL_GET_USER_BY_ID = "SELECT * FROM users WHERE u_id = ?";
        public static final String GET_NUMBER_OF_USERS_RECORDS = "SELECT COUNT(u_id) AS numberOfRecords FROM users %s";
        public static final String SQL_GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
        public static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE u_id = ?";
        public static final String SQL_CREATE_USER = "INSERT INTO users(notification, phone_number, account, status, password, " +
            "first_name, last_name, email, role_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        public static final String SQL_UPDATE_USER = "UPDATE users SET notification = ?," +
                " phone_number = ?, account = ?, status = ?, password = ?, first_name = ?, " +
                "last_name = ?, email = ?, role_id = ? WHERE u_id = ?";
        public static final String SQL_UPDATE_USER_ACCOUNT = "UPDATE users SET account = ? WHERE u_id = ?";
    }

    static class RequestSQL {
        public static final String SQL_SELECT_ALL_REQUEST = "SELECT * FROM request";
        public static final String SQL_SELECT_ALL_USER_REQUEST = "SELECT * FROM request where users_id = ?";
        public static final String SQL_SELECT_ALL_CRAFTSMAN_REQUEST = "SELECT * FROM request where isnull(repairer_id) or repairer_id = ?";
        public static final String SQL_GET_REQUEST_BY_ID = "SELECT * FROM request WHERE r_id = ?";
        public static final String GET_NUMBER_OF_REQUEST_RECORDS = "SELECT COUNT(r_id) AS numberOfRecords FROM request %s";
        public static final String GET_NUMBER_OF_USER_REQUEST_RECORDS = "SELECT COUNT(r_id) AS numberOfRecords FROM request where users_id = ? %s";
        public static final String SQL_DELETE_REQUEST_BY_ID = "DELETE FROM request WHERE id = ?";
        public static final String SQL_CREATE_REQUEST = "INSERT INTO request(users_id, descriptions, date, completion_status_id, " +
                "payment_status_id, total_cost) VALUES(?, ?, ?, ?, ?, ?)";
        public static final String SQL_UPDATE_REQUEST = "UPDATE request SET " +
                "completion_status_id = ?, payment_status_id = ?, total_cost = ? WHERE r_id = ?";
        public static final String SQL_UPDATE_REQUEST_SET_START = "UPDATE request SET " +
                "repairer_id = ?, completion_status_id = ? WHERE r_id = ?";
        public static final String SQL_UPDATE_REPAIRER_FOR_REQUEST = "UPDATE request SET " +
                "repairer_id = ? WHERE r_id = ?";
        public static final String SQL_SELECT_REQUESTS_BY_COMPLETION_STATUS = "SELECT * FROM request WHERE completion_status_id = ?";
        public static final String SQL_SELECT_REQUESTS_BY_PAYMENT_STATUS = "SELECT * FROM request WHERE payment_status_id = ?";
        public static final String SQL_UPDATE_REQUEST_PAYMENTSTATUS = "UPDATE request SET payment_status_id = ? WHERE r_id = ?";
    }
    static class FeedbackSQL {
        public static final String SQL_SELECT_ALL_FEEDBACKS = "SELECT * FROM feedback";
        public static final String SQL_GET_FEEDBACK_BY_ID = "SELECT * FROM feedback WHERE id = ?";
        public static final String GET_NUMBER_OF_FEEDBACK_RECORDS = "SELECT COUNT(feedback_id) AS numberOfRecords FROM feedback %s";
        public static final String SQL_DELETE_FEEDBACK_BY_ID = "DELETE FROM feedback WHERE id = ?";
        public static final String SQL_CREATE_FEEDBACK = "INSERT INTO feedback(date_time, " +
                "feedback_text, rating, request_id) VALUES(?, ?, ?, ?)";
        public static final String SQL_UPDATE_FEEDBACK = "UPDATE feedback SET repairer_id = ?, date_time = ?, " +
                "feedback_text = ?, rating = ?, request_id = ? WHERE id = ?";

    }
}
