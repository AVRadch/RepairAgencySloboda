package com.my.repairagency007.model.DAO;

import com.my.repairagency007.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDAO<T> {
    List<T> findAll(String query) throws DAOException;

    T getEntityById(int id) throws DAOException;

    boolean delete(T t) throws DAOException;

    boolean deleteById(int id) throws DAOException;

    boolean create(T t) throws DAOException;

    T update(T t) throws DAOException;

    public int getNumberOfRecords(String filter) throws DAOException;
}
