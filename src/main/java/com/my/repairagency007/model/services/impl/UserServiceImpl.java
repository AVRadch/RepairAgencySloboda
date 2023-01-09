package com.my.repairagency007.model.services.impl;

import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.DAO.UserDAO;
import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.entity.User;
import com.my.repairagency007.model.services.UserService;
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


    public UserDTO getById(int id) throws ServiceException{
        UserDTO userDTO;
        try {
            User user = userDAO.getEntityById(id);
            userDTO = convertUserToDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTO;
    }

    @Override
    public List<UserDTO> getAll(String query) throws ServiceException {
        List<UserDTO> userDTOS = new ArrayList<>();
        try {
            log.debug("Try to execute userDAO findAll method");
            List<User> users = userDAO.findAll(query);
            log.debug("convert users to dto");
            for (User user: users){
                log.debug("Role user = " + user.getRoleId());
                userDTOS.add(convertUserToDTO(user));
                log.debug("Role userDTO " + convertUserToDTO(user).getRole());
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
            if (userDAO.deleteById(id)){
                log.error("Can't delete user by id");
                throw new ServiceException();
            }
        } catch (DAOException e) {
            log.error("Can't delete user by id", e);
            throw new ServiceException(e);
        }
    }

    public void create(UserDTO userDTO) throws ServiceException {
        User user = convertDTOToUser(userDTO);
        try {
            userDAO.create(user);
        } catch (DAOException e) {
            log.error("Error create user", e);
            throw new ServiceException(e);
        }
    }

    public void update(UserDTO userDTO) throws ServiceException {
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
            User user = userDAO.getByEmail(email);
            userDTO = convertUserToDTO(user);
        } catch (DAOException e) {
            log.error("Error get by email", e);
            throw new ServiceException(e);
        }
        return userDTO;
    }

    public int getNumberOfRecords(String filter) throws ServiceException {
        return 0;
    }
}
