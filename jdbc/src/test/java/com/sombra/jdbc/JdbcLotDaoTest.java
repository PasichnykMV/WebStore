package com.sombra.jdbc;

import com.sombra.jdbc.TestUtil.TestUtils;
import com.sombra.model.City;
import com.sombra.model.Lot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class JdbcLotDaoTest {
    private JdbcLotDao lotDao;
    @Before
    public void setUp() throws Exception {
        lotDao = (JdbcLotDao) JdbcDaoFactory.getInstance().getDaoByClass(Lot.class);
        TestUtils.clearAndInsertIntoDB();
    }
    @Test
    public void testPersist() throws Exception {
        Lot lot = new Lot();
        lot.setTitle("Knife");
        lot.setPrice(20.50);
        lot.setCreationDate(new Date());

        lot.setCity(JdbcDaoFactory.getInstance().getCityDao().getById(1));
        lot.setCategory(JdbcDaoFactory.getInstance().getCategoriesDao().getById(8));

        lot.setId(lotDao.persist(lot).getId());

        assertEquals("Knife", lotDao.getById(lot.getId()).getTitle());
    }

    @Test
    public void testGetById() throws Exception {
        Lot lot = lotDao.getById(1);
        assertEquals("Knife", lot.getTitle());
    }

    @Test
    public void testUpdate() throws Exception {
        Lot lot = lotDao.getById(1);
        lot.setTitle("Iphone 3");

        lotDao.update(lot);

        Lot lot_upd = lotDao.getById(lot.getId());

        assertEquals("Iphone 3", lot_upd.getTitle());
    }

    @Test
    public void testGetAll() throws Exception {
        List<Lot> cities = lotDao.getAll();
        assertTrue(cities.size() > 1);
    }
    @After
    public void afterMethod() throws Exception {
        TestUtils.clear();
    }
}