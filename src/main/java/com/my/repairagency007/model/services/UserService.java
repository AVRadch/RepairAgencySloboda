package com.my.repairagency007.model.services;

import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.entity.User;

public interface UserService extends Service<User>{

    User getByEmail(String email) throws ServiceException;
}
