package com.my.repairagency007.model.DAO;

import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.entity.Request;
import com.my.repairagency007.model.entity.User;


import java.util.List;

public interface RequestDAO extends BaseDAO<Request> {

    List<Request> findRequestByCompletionStatus() throws DAOException;

    List<Request> findRequestByPaymentStatus() throws DAOException;

    List<Request> findAllForUser(String query, int userId) throws DAOException;

    public List<Request> findAllForCraftsman(String query, int userId) throws DAOException;

    int getNumberOfUserRecords(String filter, int userId) throws DAOException;

    void updateRepairForRequest(Request request) throws DAOException;

    public boolean setPaymentStatusPaid(Request request, User user) throws DAOException;

    public void setPaymentStatusCanceled(Request request) throws DAOException;
}
