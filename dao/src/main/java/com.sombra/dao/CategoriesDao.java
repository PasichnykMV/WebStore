package com.sombra.dao;

import com.sombra.model.Category;

import java.util.List;
import java.util.Set;

/**
 * Created by Макс on 16.08.2016.
 */
public interface CategoriesDao extends AbstractDao<Category>{
    void fetchSubcategories(Category categoryId);
    List<Category> getAllWithSubcategories();
}
