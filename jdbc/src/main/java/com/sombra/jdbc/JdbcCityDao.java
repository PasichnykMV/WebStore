package com.sombra.jdbc;

import com.sombra.dao.CityDao;
import com.sombra.dao.PersistException;
import com.sombra.model.City;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Макс on 16.08.2016.
 */
public class JdbcCityDao extends JdbcAbstractDao<City> implements CityDao {

    static private final String READ_QUERY = "SELECT cities.* FROM cities where city_id = ?";
    static private final String SELECT_ALL = "SELECT * FROM cities";
    static private final String CREATE_QUERY = "INSERT INTO cities(name) VALUES (?) RETURNING city_id";
    static private final String UPDATE_QUERY = "UPDATE cities SET name = ? WHERE city_id = ?";
    static private final String DELETE_QUERY = "DELETE FROM cities c WHERE c.city_id = ?";

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
    protected void fillStatementUpdate(City entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setInt(2, entity.getId());
    }

    @Override
    protected void fillStatementCreate(City entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
    }

    @Override
    protected List<City> parseResultRow(ResultSet resultSet) throws SQLException, PersistException {
        List<City> cities = new ArrayList<>();
        while (resultSet.next()) {
            City city = new City();
            city.setId(resultSet.getInt("city_id"));
            city.setName(resultSet.getString("name"));
            cities.add(city);
        }
        return cities;
    }
}
