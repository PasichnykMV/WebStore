package com.sombra.services;

import java.util.List;

/**
 * Created by Макс on 17.08.2016.
 */
public interface GenericService<T, D> {
    T save(T t) throws ServiceException;
    void delete(Integer id) throws ServiceException;
    void update(T t) throws ServiceException;
    T getById(Integer id) throws ServiceException;
    List<T> getAll() throws ServiceException;
}
