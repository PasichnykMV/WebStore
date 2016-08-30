package com.sombra.services;

import com.sombra.jdbc.JdbcDaoFactory;
import com.sombra.model.User;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by PasichnykMV on 19.08.2016.
 */
public class JdbcUserService implements UserService {
    protected final static Logger LOGGER = Logger.getLogger(JdbcUserService.class.getName());

    @Override
    public User save(User user) throws ServiceException {
        return JdbcDaoFactory.getInstance().getUserDao().persist(user);
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        JdbcDaoFactory.getInstance().getUserDao().delete(id);
    }

    @Override
    public void update(User user) throws ServiceException {
        JdbcDaoFactory.getInstance().getUserDao().update(user);
    }

    @Override
    public User getById(Integer id) throws ServiceException {
        return JdbcDaoFactory.getInstance().getUserDao().getById(id);
    }

    @Override
    public List<User> getAll() throws ServiceException {
        return JdbcDaoFactory.getInstance().getUserDao().getAll();
    }

    @Override
    public Integer getIdByEmail(String email) {
        LOGGER.info("Inside JdbcUserService");
        return JdbcDaoFactory.getInstance().getUserDao().getIdByEmail(email);
    }

    @Override
    public void addLotToUsersCart(Integer userId, Integer lotId){
        JdbcDaoFactory.getInstance().getUserDao().addLotToUsersCart(userId, lotId);
        LOGGER.info("Finish JdbcUserService addLotToUsersCart method");
    }

    @Override
    public Integer getLotsIncartCount(Integer userId) {
        return JdbcDaoFactory.getInstance().getUserDao().getCountLotsInUsersCart(userId);
    }

}
