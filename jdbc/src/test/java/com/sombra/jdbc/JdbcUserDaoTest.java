package com.sombra.jdbc;

import com.sombra.dao.PersistException;
import com.sombra.jdbc.TestUtil.TestUtils;
import com.sombra.model.User;
import com.sombra.model.UserRole;
import com.sombra.jdbc.JdbcDaoFactory;
import com.sombra.jdbc.JdbcUserDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class JdbcUserDaoTest {
    private JdbcUserDao userDao;
    @Before
    public void setUp() throws Exception {
        userDao= (JdbcUserDao) JdbcDaoFactory.getInstance().getDaoByClass(User.class);
        TestUtils.clearAndInsertIntoDB();
    }
    @Test
    public void testPersist() throws Exception {
        User user = new User();
        user.setName("Name");
        user.setLastName("LastName");
        user.setPassword("999");
        user.setEmail("email@gmail.com");
        user.setCreationDate(new Date());
        user.setEnable(true);

        UserRole role = new UserRole();
        role.setName("user");
        role.setId(1);


        user.setRole(role);


        user = userDao.persist(user);
        Integer id = user.getId();
        assertNotNull(id);

        User newUser = userDao.getById(id);

        assertEquals("Name", newUser.getName());
        assertEquals("LastName", newUser.getLastName());
        assertEquals("999", newUser.getPassword());
        assertEquals("email@gmail.com", newUser.getEmail());
        assertEquals(true, newUser.is_enable());
    }

    @Test
    public void testGetById() throws Exception {
        User user = userDao.getById(1);
        assertEquals("Test1", user.getName());
        assertEquals("Test1ovich", user.getLastName());
        assertEquals("user1", user.getPassword());
        assertEquals("testuser1@gmail.com", user.getEmail());
        assertEquals(true, user.is_enable());
    }

    @Test
    public void testUpdate() throws Exception {
        User user = userDao.getById(1);

        user.setName("updatedName");
        user.setPassword("updatedPass");
        user.setLastName("updatedLastName");
        user.setEmail("updatedMail");

        UserRole role = new UserRole();
        role.setName("user");
        role.setId(1);

        user.setRole(role);

        userDao.update(user);

        User updUser = userDao.getById(user.getId());

        assertEquals("updatedName", updUser.getName());
        assertEquals("updatedPass", updUser.getPassword());
        assertEquals("updatedLastName", updUser.getLastName());
        assertEquals("updatedMail", updUser.getEmail());

        updUser.setName("Test2");
        updUser.setPassword("user2");
        updUser.setLastName("Test2ovich");
        updUser.setEmail("testuser2@gmail.com");
        userDao.update(updUser);
    }

    @Test
    public void testGetAll() throws Exception {
        List<User> users = userDao.getAll();
        assertTrue(users.size() > 2);

    }
    @After
    public void afterMethod() throws Exception {
       TestUtils.clear();
    }
}