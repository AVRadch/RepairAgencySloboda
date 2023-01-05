package com.my.repairagency007.model.services;

import com.my.repairagency007.exception.ServiceException;

import java.util.List;

public interface Service<T> {

    T getById(String idString) throws ServiceException;

    List<T> getAll() throws ServiceException;

    void update(T entity) throws ServiceException;

    void delete(String idString) throws ServiceException;
}
