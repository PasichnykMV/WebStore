package com.sombra.jdbc;

import com.sombra.dao.CategoriesDao;
import com.sombra.dao.PersistException;
import com.sombra.model.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Макс on 16.08.2016.
 */
public class JdbcCategoriesDao extends JdbcAbstractDao<Category> implements CategoriesDao {

    static private final String READ_QUERY = "SELECT categories.* FROM categories where category_id = ?";
    static private final String SELECT_ALL = "SELECT * FROM categories WHERE parent_category_id IS NULL";
    static private final String SELECT_ALL_WITH_SUBCATEGORIES = "SELECT * FROM categories";
    static private final String CREATE_QUERY = "INSERT INTO categories(title, parent_category_id) VALUES (?, ?)" +
            " RETURNING category_id";
    static private final String UPDATE_QUERY = "UPDATE categories SET title = ?, parent_category_id = ?" +
            " WHERE category_id = ?";
    static private final String FETCH_SUBCATEGORIES = "SELECT categories.* FROM categories WHERE parent_category_id = ?";
    static private final String DELETE_QUERY = "DELETE FROM categories WHERE category_id = ?";

    @Override
    protected String getEntityName() {
        return null;
    }

    @Override
    protected String getReadQuery() {
        return READ_QUERY;
    }

    @Override
    protected String getCreateQuery() {
        return CREATE_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE_QUERY;
    }

    @Override
    protected String getSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected void fillStatementUpdate(Category entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getTitle());
        if (entity.getParentId() == null){
            statement.setNull(2, java.sql.Types.INTEGER);
        } else statement.setInt(2, entity.getParentId());
        statement.setInt(3, entity.getId());
    }

    @Override
    protected void fillStatementCreate(Category entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getTitle());
        if (entity.getParentId() == null){
        statement.setNull(2, java.sql.Types.INTEGER);
        } else statement.setInt(2, entity.getParentId());
    }

    @Override
    public void fetchSubcategories(Category category) {
        CharSequence categoryId = String.valueOf(category.getId());
        CharSequence replaced = "?";
        List<Category> subcategories =
                getObjectReferencesByQuery(Category.class, FETCH_SUBCATEGORIES.replace(replaced, categoryId));
        category.setSubcategories(subcategories);
    }

    @Override
    public List<Category> getAllWithSubcategories() {
        return getObjectReferencesByQuery(Category.class, SELECT_ALL_WITH_SUBCATEGORIES);
    }

    @Override
    protected List<Category> parseResultRow(ResultSet resultSet) throws SQLException, PersistException {
        List<Category> categories = new ArrayList<>();
        while (resultSet.next()) {
            Category category = new Category();
            category.setId(resultSet.getInt("category_id"));
            category.setTitle(resultSet.getString("title"));
            category.setParentId(resultSet.getInt("parent_category_id"));
            fetchSubcategories(category);
            categories.add(category);
        }
        return categories;
    }

}
