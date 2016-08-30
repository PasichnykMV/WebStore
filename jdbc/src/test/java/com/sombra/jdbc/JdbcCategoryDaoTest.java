package com.sombra.jdbc;

import com.sombra.jdbc.TestUtil.TestUtils;
import com.sombra.model.Category;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class JdbcCategoryDaoTest {
    private JdbcCategoriesDao categoriesDao;
    @Before
    public void setUp() throws Exception {
        categoriesDao = (JdbcCategoriesDao) JdbcDaoFactory.getInstance().getDaoByClass(Category.class);
        TestUtils.clearAndInsertIntoDB();
    }
    @Test
    public void testPersist() throws Exception {
        Category category = new Category();
        category.setTitle("Goods");
        category.setId(categoriesDao.persist(category).getId());

        assertEquals("Goods", categoriesDao.getById(category.getId()).getTitle());
    }

    @Test
    public void testGetById() throws Exception {
        Category category = categoriesDao.getById(1);
        Category subcategory = categoriesDao.getById(6);

        assertEquals("Cloth", category.getTitle());
        assertNotNull(subcategory.getParentId());
    }

    @Test
    public void testUpdate() throws Exception {
        Category category = categoriesDao.getById(1);
        category.setTitle("Wear");
        category.setParentId(0);

        categoriesDao.update(category);

        Category category_upd = categoriesDao.getById(category.getId());

        assertEquals("Wear", category_upd.getTitle());
    }

    @Test
    public void testGetAll() throws Exception {
        List<Category> categories = categoriesDao.getAll();
        assertTrue(categories.size() > 1);
    }

    @After
    public void afterMethod() throws Exception {
        TestUtils.clear();
    }
}