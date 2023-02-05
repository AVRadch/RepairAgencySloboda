package com.my.repairagency007.model.services.impl;

import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.exception.IncorrectPasswordException;
import com.my.repairagency007.exception.NoSuchUserException;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.DAO.UserDAO;
import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.entity.User;
import com.my.repairagency007.model.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.my.repairagency007.util.MapperDTOUtil.convertDTOToUser;
import static com.my.repairagency007.util.MapperDTOUtil.convertUserToDTO;

public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDAO userDAO;

    public UserServiceImpl (UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDTO login(String email, String password) throws ServiceException {

        UserDTO userDTO;

        try {
            User user;
            log.info("Try to userDAO.getByEmail email = " + email);
            user = userDAO.getByEmail(email).orElseThrow(NoSuchUserException::new);
            log.info("Try to convert to userDTO");
            userDTO = convertUserToDTO(user);
        } catch (DAOException e) {
            log.error("Error get user by email", e);
            throw new ServiceException(e);
        }
        return userDTO;
    }

    public List<UserDTO> getAllRepairer() throws ServiceException{
        List<UserDTO> userDTOS = new ArrayList<>();
        String query = " where role_id=2";
        userDTOS = getAll(query);
        return userDTOS;
    }

    @Override
    public UserDTO getById(int id) throws ServiceException{
        UserDTO userDTO = null;
        try {
            User user = userDAO.getEntityById(id).orElse(null);
            if (user != null) {
                userDTO = convertUserToDTO(user);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTO;
    }

    @Override
    public List<UserDTO> getAll(String query) throws ServiceException {
        List<UserDTO> userDTOS = new ArrayList<>();
        try {
            log.debug("Try to execute userDAO findAll method.");
            List<User> users = userDAO.findAll(query);
            log.debug("convert users to dto");
            for (User user: users){
                userDTOS.add(convertUserToDTO(user));
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTOS;
    }


    public void delete(UserDTO user) throws ServiceException {
        delete(user.getId());
    }

    public void delete(int id) throws ServiceException {
        try {
            if (!userDAO.deleteById(id)){
                log.error("Can't delete user by id");
                throw new ServiceException();
            }
        } catch (DAOException e) {
            log.error("Can't delete user by id", e);
            throw new ServiceException(e);
        }
        log.debug("Delete user with id = " + id);
    }

    public void create(UserDTO userDTO) throws ServiceException {
        log.debug("Try to convert userDTO to user " + userDTO);
        User user = convertDTOToUser(userDTO);
        try {
            userDAO.create(user);
        } catch (DAOException e) {
            log.error("Error create user", e);
            throw new ServiceException(e);
        }
    }

    public void update(UserDTO userDTO) throws ServiceException {
        log.debug("Update user. UserDTO = " + userDTO);
        User user = convertDTOToUser(userDTO);
        try {
            userDAO.update(user);
        } catch (DAOException e) {
            log.error("Error update user", e);
            throw new ServiceException(e);
        }
    }

    public UserDTO getByEmail(String email) throws ServiceException {
        UserDTO userDTO;
        try {
            User user = userDAO.getByEmail(email).orElseThrow(NoSuchUserException::new);
            userDTO = convertUserToDTO(user);
        } catch (DAOException e) {
            log.error("Error get by email", e);
            throw new ServiceException(e);
        }
        return userDTO;
    }

    public int getNumberOfRecords(String filter) throws ServiceException {
        int records;
        try {
            records = userDAO.getNumberOfRecords(filter);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return records;
    }
}
