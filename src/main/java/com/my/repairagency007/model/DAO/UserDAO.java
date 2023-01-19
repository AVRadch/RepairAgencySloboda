package com.my.repairagency007.model.DAO;

import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.entity.User;

import java.util.Optional;

public interface UserDAO extends BaseDAO<User> {

    Optional<User> getByEmail(String email) throws DAOException;

}
