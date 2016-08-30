package com.sombra.services;

import com.sombra.dao.CategoriesDao;
import com.sombra.model.Category;

import java.util.List;

/**
 * Created by Макс on 17.08.2016.
 */
public interface CategoryService extends GenericService<Category, CategoriesDao> {
    List<Category> getAllWithSubcategories();
}
