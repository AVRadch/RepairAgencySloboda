package com.my.repairagency007.model.services.impl;

import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.DAO.UserDAO;
import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.entity.User;
import com.my.repairagency007.model.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);

    private final UserDAO userDAO;

    public UserServiceImpl (UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getAll(String query) throws ServiceException {
        List<User> users;
        try {
            users = userDAO.findAll(query);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return users;
    }

    public User getById(int id) throws ServiceException{
        User user;
        try {
            user = userDAO.getEntityById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    public void delete(User user) throws ServiceException {
        delete(user.getId());
    }

    public void delete(int id) throws ServiceException {
        try {
            if (userDAO.deleteById(id)){
                log.error("Can't delete user by id");
                throw new ServiceException();
            }
        } catch (DAOException e) {
            log.error("Can't delete user by id", e);
            throw new ServiceException(e);
        }
    }

    public void create(User user) throws ServiceException {
        try {
            userDAO.create(user);
        } catch (DAOException e) {
            log.error("Error create user", e);
            throw new ServiceException(e);
        }
    }

    public void update(User user) throws ServiceException {
        try {
            userDAO.update(user);
        } catch (DAOException e) {
            log.error("Error update user", e);
            throw new ServiceException(e);
        }
    }

    public User getByEmail(String email) throws ServiceException {
        User user;
        try {
            user = userDAO.getByEmail(email);
        } catch (DAOException e) {
            log.error("Error get by email", e);
            throw new ServiceException(e);
        }
        return user;
    }
}
