package com.sombra.jdbc;

import com.sombra.dao.PersistException;
import com.sombra.dao.UserDao;
import com.sombra.model.Lot;
import com.sombra.model.User;
import com.sombra.model.UserRole;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Макс on 16.08.2016.
 */
public class JdbcUserDao extends JdbcAbstractDao<User> implements UserDao {

    protected final static Logger LOGGER = Logger.getLogger(JdbcUserDao.class.getName());

    static private final String READ_QUERY = "SELECT users.* FROM USERS WHERE user_id = ?";
    static private final String SELECT_ALL = "SELECT * FROM users WHERE role_id=1";
    static private final String CREATE_QUERY = "INSERT INTO " +
            "users(email,password,name,last_name,creation_date, is_enable, role_id) " +
            "VALUES (?, ?, ? , ?, ?, ?, ?) RETURNING user_id";
    static private final String UPDATE_QUERY = "UPDATE users  " +
            "SET email = ? ," +
            "    password = ? ," +
            "    name = ? ," +
            "    last_name = ? ," +
            "    creation_date = ?," +
            "    is_enable =?" +
            "WHERE user_id = ?";
    static private final String GET_ID_BY_EMAIL = "SELECT u.user_id FROM users u WHERE u.email LIKE '";
    private static final String CREATE_QUERY_USER_LOTS = "INSERT INTO user_lots(user_id, lot_id) VALUES (?,?)";
    private static final String GET_LOTS_IN_USERS_CART_COUNT = "SELECT COUNT(*) FROM user_lots WHERE user_id = ?";

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
        return SELECT_ALL;
    }

    @Override
    protected void fillStatementUpdate(User entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getEmail());
        statement.setString(2, entity.getPassword());
        statement.setString(3, entity.getName());
        statement.setString(4, entity.getLastName());
        statement.setDate(5, new Date(entity.getCreationDate().getTime()));
        statement.setBoolean(6, entity.is_enable());
        statement.setInt(7, entity.getId());
    }

    @Override
    protected void fillStatementCreate(User entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getEmail());
        statement.setString(2, entity.getPassword());
        statement.setString(3, entity.getName());
        statement.setString(4, entity.getLastName());
        statement.setDate(5, new Date(entity.getCreationDate().getTime()));
        statement.setBoolean(6, true);
        statement.setInt(7, entity.getRole().getId());
    }

    protected  void fillAddLotToUsersCart( Integer userId, Integer lotId, PreparedStatement statement)
            throws  SQLException {
        statement.setInt(1, userId);
        statement.setInt(2, lotId);
    }

    @Override
    protected List<User> parseResultRow(ResultSet resultSet) throws SQLException, PersistException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("user_id"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setName(resultSet.getString("name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setCreationDate(resultSet.getDate("creation_date"));
            user.setEnable(resultSet.getBoolean("is_enable"));
            UserRole role = getObjectReferenceById(UserRole.class, resultSet.getInt("role_id"));
            user.setRole(role);

            users.add(user);
        }
        return users;
    }

    @Override
    public Integer getIdByEmail(String email) throws PersistException {
        LOGGER.info("Inside JdbcUserDao");
        Integer id = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try{
            connection = JdbcDaoFactory.getInstance().getConnection();
            LOGGER.info("Connected");
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(GET_ID_BY_EMAIL).append(email).append("'");
            rs = statement.executeQuery(query.toString());
            LOGGER.info("get resultset");
            while (rs.next()) {
                id = rs.getInt("user_id");
            }

            return id;
        } catch (SQLException e) {
            LOGGER.error("exception in " + this.getClass().getSimpleName(), e);
            throw new PersistException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOGGER.error("Can`t close connection, exception in " + this.getClass().getSimpleName(), e);
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOGGER.error("Can`t close statement, exception in " + this.getClass().getSimpleName(), e);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOGGER.error("Can`t close rs, exception in " + this.getClass().getSimpleName(), e);
                }
            }
        }
    }

    @Override
    public void addLotToUsersCart(Integer userId, Integer lotId){
        LOGGER.info("Start addLotToUsersCart with params: userId=" + userId + ", lotId=" +lotId);
        Connection connection = null;
        PreparedStatement statement = null;
        try{connection = JdbcDaoFactory.getInstance().getConnection();
            statement = connection.prepareStatement(CREATE_QUERY_USER_LOTS);
            fillAddLotToUsersCart(userId, lotId , statement);
            LOGGER.info("Filled with lot_id "+lotId +" user_id "+userId+" cart");
            statement.executeQuery();
        } catch (SQLException e) {
            throw new PersistException(e);
        } finally {
            closeConnection(connection);
            try {
                if (statement != null) {
                    statement.close();
                    LOGGER.info("Statement closed");
                }
            } catch (SQLException e) {
                LOGGER.error("Can`t close statement, exception in " + this.getClass().getSimpleName(), e);
            }
        }
    }

    @Override
    public Integer getCountLotsInUsersCart(Integer userId) {
        LOGGER.info("Inside getCountLotsInUsersCart JdbcUserDao");
        Integer lotsCount = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try{
            connection = JdbcDaoFactory.getInstance().getConnection();
            LOGGER.info("Connected");
            statement = connection.createStatement();
            String query = GET_LOTS_IN_USERS_CART_COUNT.replace("?", userId.toString());
            rs = statement.executeQuery(query.toString());
            LOGGER.info("get resultset");
            while (rs.next()) {
                lotsCount = rs.getInt(1);
            }

            return lotsCount;
        } catch (SQLException e) {
            LOGGER.error("exception in " + this.getClass().getSimpleName(), e);
            throw new PersistException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOGGER.error("Can`t close connection, exception in " + this.getClass().getSimpleName(), e);
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOGGER.error("Can`t close statement, exception in " + this.getClass().getSimpleName(), e);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOGGER.error("Can`t close rs, exception in " + this.getClass().getSimpleName(), e);
                }
            }
        }
    }

}
