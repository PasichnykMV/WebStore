package com.sombra.jdbc;

import com.sombra.dao.LotDao;
import com.sombra.dao.PersistException;
import com.sombra.model.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class JdbcLotDao extends JdbcAbstractDao<Lot> implements LotDao {

    protected final static Logger LOGGER = Logger.getLogger(JdbcLotDao.class.getName());

    static private final String READ_QUERY = "SELECT lots.* FROM lots WHERE lot_id = ?";
    static private final String SELECT_ALL = "SELECT * FROM lots";
    static private final String CREATE_QUERY = "INSERT INTO " +
            "lots(title,description, price, creation_date, city_id, category_id) " +
            "VALUES (?, ?, ?, ?, ?, ?) RETURNING lot_id";
    static private final String UPDATE_QUERY = "UPDATE lots  " +
            "SET title = ? ," +
            "    description = ? ," +
            "    price = ? ," +
            "    creation_date = ?," +
            "    city_id = ?," +
            "    category_id = ?" +
            "WHERE lot_id = ?";
    static private final String GET_ALL_IN_USERS_CART = "SELECT l.* FROM lots l JOIN user_lots ul " +
            "ON l.lot_id = ul.lot_id WHERE ul.user_id = ?";
    static private final String DELETE_LOT_FROM_CART = "DELETE FROM user_lots WHERE user_id =? AND lot_id=?";
    static private final String DELETE_ALL_FROM_CART = "DELETE FROM user_lots WHERE user_id =?";
    static private final String CREATE_QUERY_USER_LOTS_TO_ORDER = "INSERT INTO users_orders(user_id, lot_id) VALUES (?,?)";
    static private final String GET_ALL_FOR_CATEGORY = "SELECT l.* FROM lots l JOIN categories c " +
            "ON l.category_id = c.category_id WHERE c.parent_category_id = ";
    static private final String FETCH_IMAGES = "SELECT im.* FROM images im JOIN lots_images lim " +
            "ON im.image_id=lim.image_id WHERE lim.lot_id = ?";
    static private final String GET_ALL_USERS_ORDERS = "SELECT l.* FROM lots l JOIN users_orders uor " +
            "ON l.lot_id=uor.lot_id WHERE uor.user_id = ?";

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
    protected void fillStatementUpdate(Lot entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getTitle());
        statement.setString(2,entity.getDescription());
        statement.setDouble(3, entity.getPrice());
        statement.setDate(4, new Date(entity.getCreationDate().getTime()));
        statement.setInt(5, entity.getCity().getId());
        statement.setInt(6, entity.getCategory().getId());
        statement.setInt(7, entity.getId());
    }

    @Override
    protected void fillStatementCreate(Lot entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getTitle());
        statement.setString(2,entity.getDescription());
        statement.setDouble(3, entity.getPrice());
        statement.setDate(4, new Date(entity.getCreationDate().getTime()));
        statement.setInt(5, entity.getCity().getId());
        statement.setInt(6, entity.getCategory().getId());
    }

    protected void fillDeleteLotFromCart(Integer userId, Integer lotId, PreparedStatement statement)
            throws SQLException {
        statement.setInt(1,userId);
        statement.setInt(2, lotId);
    }

    protected void fillDeleteAllFromCart(Integer userId, PreparedStatement statement)
            throws SQLException {
        statement.setInt(1,userId);
    }

    protected  void fillAddLotToUsersOrder( Integer userId, Integer lotId, PreparedStatement statement)
            throws  SQLException {
        statement.setInt(1, userId);
        statement.setInt(2, lotId);
    }

    @Override
    protected List<Lot> parseResultRow(ResultSet resultSet) throws SQLException, PersistException {
        List<Lot> lots = new ArrayList<>();
        while (resultSet.next()) {
            Lot lot = new Lot();
            lot.setId(resultSet.getInt("lot_id"));
            lot.setTitle(resultSet.getString("title"));
            lot.setPrice(resultSet.getDouble("price"));
            lot.setCreationDate(resultSet.getDate("creation_date"));
            lot.setDescription(resultSet.getString("description"));

            City city = getObjectReferenceById(City.class, resultSet.getInt("city_id"));
            lot.setCity(city);

            Category category = getObjectReferenceById(Category.class, resultSet.getInt("category_id"));
            lot.setCategory(category);
            fetchImages(lot);
            lots.add(lot);
        }
        return lots;
    }

    @Override
    public void fetchImages(Lot lot) {
        CharSequence replaced = "?";
        List<Image> images = (getObjectReferencesByQuery(Image.class,
                FETCH_IMAGES.replace(replaced, lot.getId().toString())));
        lot.setImages(images);
    }

    @Override
    public List<Lot> getAllUsersOrders(Integer userId) {
        CharSequence replaced = "?";
        return  (getObjectReferencesByQuery(Lot.class, GET_ALL_USERS_ORDERS.replace(replaced, userId.toString())));
    }

    @Override
    public List<Lot> getAllLotsInUserCart(Integer userId) throws PersistException {
        return getObjectReferencesByQuery(Lot.class, GET_ALL_IN_USERS_CART.replace("?", userId.toString()));
    }

    @Override
    public void deleteLotFromCart(Integer userId, Integer lotId) throws PersistException {
        LOGGER.info("Start deleteLotFromCart with params: userId=" + userId + ", lotId=" +lotId);
        Connection connection = null;
        PreparedStatement statement = null;
        try{connection = JdbcDaoFactory.getInstance().getConnection();
            statement = connection.prepareStatement(DELETE_LOT_FROM_CART);
            fillDeleteLotFromCart(userId, lotId , statement);
            statement.executeUpdate();
            LOGGER.info("Deleted lot_id "+lotId +" of user_id "+userId+" cart");
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
    public void deleteAllFromCart (Integer userId) throws PersistException {
        LOGGER.info("Start deleteAllFromCart with params: userId=" + userId);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = JdbcDaoFactory.getInstance().getConnection();
            statement = connection.prepareStatement(DELETE_ALL_FROM_CART);
            fillDeleteAllFromCart(userId, statement);
            statement.executeUpdate();
            LOGGER.info("Deleted all at user_id " + userId + " cart");
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
    public void addLotToUsersOrder(Integer userId, Integer lotId){
        LOGGER.info("Start addLotToUsersOrder with params: userId=" + userId + ", lotId=" +lotId);
        Connection connection = null;
        PreparedStatement statement = null;
        try{connection = JdbcDaoFactory.getInstance().getConnection();
            statement = connection.prepareStatement(CREATE_QUERY_USER_LOTS_TO_ORDER);
            fillAddLotToUsersOrder(userId, lotId , statement);
            LOGGER.info("Filled with lot_id "+lotId +" user_id "+userId+" cart");
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistException(e);
        } finally {
            closeConnection(connection);
            LOGGER.info("Connection closed...");
            try {
                if (statement != null) {
                    statement.close();
                    LOGGER.info("Statement closed...");
                }
            } catch (SQLException e) {
                LOGGER.error("Can`t close statement, exception in " + this.getClass().getSimpleName(), e);
            }
        }
    }

    @Override
    public List<Lot> getAllForCategory(Set<Integer> set, Integer parentId) throws PersistException {
        StringBuilder query = new StringBuilder(GET_ALL_FOR_CATEGORY).append(parentId.toString());
        if (set.size() != 0){
            for (Integer i : set) {
                query.append(" OR c.category_id =").append(i.toString());
            }
        }
        return getObjectReferencesByQuery(Lot.class, query.toString());
    }

}
