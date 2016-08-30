package com.sombra.jdbc;

import com.sombra.dao.PersistException;
import com.sombra.dao.UserRoleDao;
import com.sombra.model.UserRole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Макс on 16.08.2016.
 */
public class JdbcUserRoleDao extends JdbcAbstractDao<UserRole> implements UserRoleDao {

    static private final String READ_QUERY = "SELECT user_roles.* FROM user_roles where role_id = ?";
    static private final String SELECT_ALL = "SELECT * FROM user_roles";
    static private final String CREATE_QUERY = "INSERT INTO " +
            "user_roles(name) VALUES (?) RETURNING role_id";
    static private final String UPDATE_QUERY = "UPDATE user_roles SET name = ? WHERE role_id = ?";

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
        return null;
    }

    @Override
    protected String getSelectAllQuery() {
        return SELECT_ALL  ;
    }

    @Override
    protected void fillStatementUpdate(UserRole entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setInt(2, entity.getId());
    }

    @Override
    protected void fillStatementCreate(UserRole entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
    }

    @Override
    protected List<UserRole> parseResultRow(ResultSet resultSet) throws SQLException, PersistException {
        List<UserRole> userRoles = new ArrayList<>();
        while (resultSet.next()) {
            UserRole userRole = new UserRole();
            userRole.setId(resultSet.getInt("role_id"));
            userRole.setName(resultSet.getString("name"));
            userRoles.add(userRole);
        }
        return userRoles;
    }
}
