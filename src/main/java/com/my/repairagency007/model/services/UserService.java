package com.my.repairagency007.model.services;

import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.exception.ServiceException;


public interface UserService extends Service<UserDTO>{

    UserDTO getByEmail(String email) throws ServiceException;
}
