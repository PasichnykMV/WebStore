package com.sombra.services;

import com.sombra.jdbc.JdbcDaoFactory;
import com.sombra.model.City;

import java.util.List;

/**
 * Created by PasichnykMV on 22.08.2016.
 */
public class JdbcCityService implements CityService {
    @Override
    public City save(City city) throws ServiceException {
        return JdbcDaoFactory.getInstance().getCityDao().persist(city);
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        JdbcDaoFactory.getInstance().getCityDao().delete(id);
    }

    @Override
    public void update(City city) throws ServiceException {
        JdbcDaoFactory.getInstance().getCityDao().update(city);
    }

    @Override
    public City getById(Integer id) throws ServiceException {
        return JdbcDaoFactory.getInstance().getCityDao().getById(id);
    }

    @Override
    public List<City> getAll() throws ServiceException {
        return JdbcDaoFactory.getInstance().getCityDao().getAll();
    }
}
