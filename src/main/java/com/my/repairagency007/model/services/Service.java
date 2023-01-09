package com.my.repairagency007.model.services;

import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.exception.ServiceException;

import java.util.List;

public interface Service<T> {

    T getById(int id) throws ServiceException;

    List<T> getAll(String query) throws ServiceException;

    void update(T entity) throws ServiceException;

    void delete(int id) throws ServiceException;
}
