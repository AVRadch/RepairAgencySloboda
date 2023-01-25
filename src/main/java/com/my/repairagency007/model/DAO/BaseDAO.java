package com.my.repairagency007.model.DAO;

import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface BaseDAO<T> {
    List<T> findAll(String query) throws DAOException;

    Optional<T> getEntityById(int id) throws DAOException;

    boolean delete(T t) throws DAOException;

    boolean deleteById(int id) throws DAOException;

    boolean create(T t) throws DAOException;

    T update(T t) throws DAOException;

    public int getNumberOfRecords(String filter) throws DAOException;
}
