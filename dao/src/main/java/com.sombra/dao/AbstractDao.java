package com.sombra.dao;

import java.io.Serializable;
import java.util.List;

public interface AbstractDao<T extends Serializable> {
    T persist(T entity) throws PersistException;

    T getById(Integer id) throws PersistException;

    List<T> getAll() throws PersistException;

    boolean delete(Integer id) throws PersistException;

    boolean update(T entity) throws PersistException;
}