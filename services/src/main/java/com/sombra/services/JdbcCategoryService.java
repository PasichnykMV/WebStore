package com.sombra.services;

import com.sombra.jdbc.JdbcDaoFactory;
import com.sombra.model.Category;

import java.util.List;

/**
 * Created by Макс on 17.08.2016.
 */
public class JdbcCategoryService implements CategoryService {

    public Category save(Category category) throws ServiceException {
        return JdbcDaoFactory.getInstance().getCategoriesDao().persist(category);
    }

    public void delete(Integer id) throws ServiceException {
        JdbcDaoFactory.getInstance().getCategoriesDao().delete(id);
    }

    public void update(Category category) throws ServiceException {

    }

    public Category getById(Integer id) throws ServiceException {
        return JdbcDaoFactory.getInstance().getCategoriesDao().getById(id);
    }

    public List<Category> getAll() throws ServiceException {
        return JdbcDaoFactory.getInstance().getCategoriesDao().getAll();
    }

    public List<Category> getAllWithSubcategories() throws ServiceException {
        return JdbcDaoFactory.getInstance().getCategoriesDao().getAllWithSubcategories();
    }
}
