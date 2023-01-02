package com.my.repairagency007.DAO;

import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.entity.User;

import java.util.List;

public interface UserDAO extends BaseDAO<User> {

    User getByEmail(String email) throws DAOException;
}
