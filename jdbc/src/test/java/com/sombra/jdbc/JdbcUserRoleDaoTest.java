package com.sombra.jdbc;

import com.sombra.jdbc.TestUtil.TestUtils;
import com.sombra.model.UserRole;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class JdbcUserRoleDaoTest {
    private JdbcUserRoleDao userRoleDao;
    @Before
    public void setUp() throws Exception {
        userRoleDao= (JdbcUserRoleDao) JdbcDaoFactory.getInstance().getDaoByClass(UserRole.class);
        TestUtils.clearAndInsertIntoDB();
    }
    @Test
    public void testPersist() throws Exception {
        UserRole user_role = new UserRole();
        user_role.setName("user");
        user_role.setId(userRoleDao.persist(user_role).getId());

        UserRole admin_role = new UserRole();
        admin_role.setName("admin");
        admin_role.setId(userRoleDao.persist(admin_role).getId());

        assertEquals("user", userRoleDao.getById(user_role.getId()).getName());
        assertEquals("admin", userRoleDao.getById(admin_role.getId()).getName());
    }

    @Test
    public void testGetById() throws Exception {
        UserRole user_role = userRoleDao.getById(1);
        assertEquals("user", user_role.getName());
    }

    @Test
    public void testUpdate() throws Exception {
        UserRole role = userRoleDao.getById(1);
        role.setName("admin");

        userRoleDao.update(role);

        UserRole role1 = userRoleDao.getById(role.getId());

        assertEquals("admin", role1.getName());
    }

    @Test
    public void testGetAll() throws Exception {
        List<UserRole> roles = userRoleDao.getAll();
        assertTrue(roles.size() > 1);
    }
    @After
    public void afterMethod() throws Exception {
        TestUtils.clear();
    }
}