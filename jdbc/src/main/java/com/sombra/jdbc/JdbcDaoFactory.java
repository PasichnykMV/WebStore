package com.sombra.jdbc;

import com.sombra.dao.*;
import com.sombra.model.*;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Макс on 16.08.2016.
 */
public class JdbcDaoFactory implements DaoFactory {

    private static JdbcDaoFactory factory = null;
    private static JdbcUserDao userDao = null;
    private static JdbcUserRoleDao userRoleDao = null;
    private static JdbcLotDao lotDao = null;
    private static JdbcCategoriesDao categoriesDao = null;
    private static JdbcImageDao imageDao = null;
    private static JdbcCityDao cityDao = null;

    private DataSource dataSource = null;

    protected JdbcDaoFactory() {
    }

    public static JdbcDaoFactory getInstance() {
        if (factory == null) {
            factory = new JdbcDaoFactory();
        }
        return factory;
    }

    protected Connection getConnection() throws SQLException {
        if (dataSource == null) {
            dataSource = getDefaulDataSource();
        }
        return dataSource.getConnection();
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private DataSource getDefaulDataSource() {

        Properties dbConfig = new Properties();
        try {
            dbConfig.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("dbConfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BasicDataSource bds = new BasicDataSource();

        bds.setDriverClassName(dbConfig.getProperty("db.driver"));
        bds.setUrl(dbConfig.getProperty("db.url"));
        bds.setUsername(dbConfig.getProperty("db.username"));
        bds.setPassword(dbConfig.getProperty("db.password"));

        return bds;

    }

    protected <T extends Serializable> JdbcAbstractDao<T> getDaoByClass(Class<T> entityClass) throws PersistException {

        JdbcAbstractDao dao;
        if (entityClass == Lot.class) {
            dao =(JdbcAbstractDao) getLotDao();
        } else if (entityClass == UserRole.class) {
            dao =(JdbcAbstractDao) getUserRoleDao();
        } else if (entityClass == City.class) {
            dao = (JdbcAbstractDao) getCityDao();
        } else if (entityClass == Category.class) {
            dao = (JdbcAbstractDao) getCategoriesDao();
        } else if (entityClass == Image.class) {
            dao = (JdbcAbstractDao) getImageDao();
        } else if (entityClass == User.class) {
            dao = (JdbcAbstractDao) getUserDao();
        } else {
            throw new UnsupportedOperationException("Dao for " + entityClass.getName() + " is not exist");
        }
        return dao;
    }

    @Override
    public CityDao getCityDao() {
        if (cityDao == null)
            cityDao = new JdbcCityDao();
        return cityDao;
    }

    @Override
    public UserRoleDao getUserRoleDao() {
        if (userRoleDao == null)
            userRoleDao = new JdbcUserRoleDao();
        return userRoleDao;
    }

    @Override
    public ImageDao getImageDao() {
        if (imageDao == null)
            imageDao = new JdbcImageDao();
        return imageDao;
    }

    @Override
    public LotDao getLotDao() throws PersistException {
        if (lotDao == null) {
            lotDao = new JdbcLotDao();
        }
        return lotDao;
    }

    @Override
    public CategoriesDao getCategoriesDao() throws PersistException {
        if (categoriesDao == null) {
            categoriesDao = new JdbcCategoriesDao();
        }
        return categoriesDao;
    }

    @Override
    public UserDao getUserDao() throws PersistException {
        if (userDao == null) {
            userDao = new JdbcUserDao();
        }
        return userDao;
    }
}