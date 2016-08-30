package com.sombra.dao;

public interface DaoFactory {

    UserDao getUserDao() throws PersistException;

    UserRoleDao getUserRoleDao() throws PersistException;

    CityDao getCityDao() throws PersistException;

    CategoriesDao getCategoriesDao() throws PersistException;

    LotDao getLotDao() throws PersistException;

    ImageDao getImageDao() throws PersistException;

}