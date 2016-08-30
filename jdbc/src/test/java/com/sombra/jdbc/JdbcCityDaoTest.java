package com.sombra.jdbc;

import com.sombra.jdbc.TestUtil.TestUtils;
import com.sombra.model.City;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class JdbcCityDaoTest {
    private JdbcCityDao cityDao;
    @Before
    public void setUp() throws Exception {
        cityDao = (JdbcCityDao) JdbcDaoFactory.getInstance().getDaoByClass(City.class);
        TestUtils.clearAndInsertIntoDB();
    }
    @Test
    public void testPersist() throws Exception {
        City city = new City();
        city.setName("Kharkiv");
        city.setId(cityDao.persist(city).getId());

        assertEquals("Kharkiv", cityDao.getById(city.getId()).getName());
    }

    @Test
    public void testGetById() throws Exception {
        City city = cityDao.getById(1);
        assertEquals("Poltava", city.getName());
    }

    @Test
    public void testUpdate() throws Exception {
        City city = cityDao.getById(1);
        city.setName("Poltava");

        cityDao.update(city);

        City city_upd = cityDao.getById(city.getId());

        assertEquals("Poltava", city_upd.getName());
    }

    @Test
    public void testGetAll() throws Exception {
        List<City> cities = cityDao.getAll();
        assertTrue(cities.size() > 1);
    }
    @After
    public void afterMethod() throws Exception {
        TestUtils.clear();
    }
}