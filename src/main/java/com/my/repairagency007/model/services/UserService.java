package com.my.repairagency007.model.services;

import com.my.repairagency007.model.DAO.implementations.UserDAOImpl;
import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.entity.User;

import java.util.List;

public class UserService {

    public List<User> findAll() throws DAOException {
        UserDAOImpl userDAO = new UserDAOImpl();
        return userDAO.findAll();
    }

    public User getEntityById(int id) throws DAOException{
        UserDAOImpl userDAO = new UserDAOImpl();
        return  userDAO.getEntityById(id);
    }

    public boolean delete(User user) throws DAOException {
        UserDAOImpl userDAO = new UserDAOImpl();
        return userDAO.delete(user);
    }

    public boolean deleteById(int id) throws DAOException {
        UserDAOImpl userDAO = new UserDAOImpl();
        return userDAO.deleteById(id);
    }

    public boolean create(User user) throws DAOException {
        UserDAOImpl userDAO = new UserDAOImpl();
        return userDAO.create(user);
    }

    public User update(User user) throws DAOException {
        UserDAOImpl userDAO = new UserDAOImpl();
        return userDAO.update(user);
    }

    public User getByEmail(String email) throws DAOException {
        UserDAOImpl userDAO = new UserDAOImpl();
        return userDAO.getByEmail(email);
    }
}
