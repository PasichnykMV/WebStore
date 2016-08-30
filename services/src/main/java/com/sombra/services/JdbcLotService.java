package com.sombra.services;

import com.sombra.jdbc.JdbcDaoFactory;
import com.sombra.model.Lot;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Set;

/**
 * Created by Макс on 18.08.2016.
 */
public class JdbcLotService implements LotService{

    protected final static Logger LOGGER = Logger.getLogger(JdbcLotService.class.getName());

    @Override
    public Lot save(Lot lot) throws ServiceException {
        return JdbcDaoFactory.getInstance().getLotDao().persist(lot);
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        JdbcDaoFactory.getInstance().getLotDao().delete(id);
    }

    @Override
    public void update(Lot lot) throws ServiceException {
        JdbcDaoFactory.getInstance().getLotDao().update(lot);
    }

    @Override
    public Lot getById(Integer id) throws ServiceException {
        return JdbcDaoFactory.getInstance().getLotDao().getById(id);
    }

    @Override
    public List<Lot> getAll() throws ServiceException {
        return JdbcDaoFactory.getInstance().getLotDao().getAll();
    }

    @Override
    public List<Lot> getAllLotsInUserCart(Integer userId) {
        return JdbcDaoFactory.getInstance().getLotDao().getAllLotsInUserCart(userId);
    }

    @Override
    public void deleteLotFromCart(Integer userId, Integer lotId) {
        JdbcDaoFactory.getInstance().getLotDao().deleteLotFromCart(userId, lotId);
    }

    @Override
    public void deleteAllFromCart(Integer userId) {
        JdbcDaoFactory.getInstance().getLotDao().deleteAllFromCart(userId);
    }

    @Override
    public void addLotToUsersOrder(Integer userId, Integer lotId) {
        JdbcDaoFactory.getInstance().getLotDao().addLotToUsersOrder(userId, lotId);
    }

    @Override
    public List<Lot> getAllForCategory(Set<Integer> set, Integer categoryId){
        return JdbcDaoFactory.getInstance().getLotDao().getAllForCategory(set, categoryId);
    }

    @Override
    public List<Lot> getAllUsersOrders(Integer userId) {
        return JdbcDaoFactory.getInstance().getLotDao().getAllUsersOrders(userId);
    }

}
