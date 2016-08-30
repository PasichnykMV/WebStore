package com.sombra.jdbc;

import com.sombra.dao.ImageDao;
import com.sombra.dao.PersistException;
import com.sombra.model.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Макс on 16.08.2016.
 */
public class JdbcImageDao extends JdbcAbstractDao<Image> implements ImageDao {

    static private final String READ_QUERY = "SELECT * FROM images where image_id = ?";
    static private final String SELECT_ALL = "SELECT * FROM images";
    static private final String CREATE_QUERY = "INSERT INTO images(name, file, is_cover) VALUES (?, ?, ?)" +
            " RETURNING image_id";
    static private final String UPDATE_QUERY = "UPDATE images SET name = ?, file = ?, is_cover = ?" +
            " WHERE image_id = ?";
    static private final String CREATE_LOTS_IMAGES = "INSERT INTO lots_images (lot_id, image_id) VALUES (?,?);";
    static private final String DELETE_QUERY = "DELETE FROM images img WHERE img.image_id = ?";

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
    protected void fillStatementUpdate(Image entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getFile());
        statement.setBoolean(3, entity.is_cover());
        statement.setInt(4, entity.getId());
    }

    @Override
    protected void fillStatementCreate(Image entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getFile());
        statement.setBoolean(3, entity.is_cover());
    }

    protected  void fillAddLotsImages(Integer lotId, Integer imageId, PreparedStatement statement)
            throws  SQLException {
        statement.setInt(1, lotId);
        statement.setInt(2, imageId);
    }

    @Override
    public void addLotImages(Integer lotId, Integer imageId){
        LOGGER.info("Start addLotImages with params: lotId=" + lotId + ", imageId=" +imageId);
        Connection connection = null;
        PreparedStatement statement = null;
        try{connection = JdbcDaoFactory.getInstance().getConnection();
            statement = connection.prepareStatement(CREATE_LOTS_IMAGES);
            fillAddLotsImages(lotId, imageId, statement);
            LOGGER.info("Filled with lot_id "+lotId +" image_id "+imageId);
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
    protected List<Image> parseResultRow(ResultSet resultSet) throws SQLException, PersistException {
        List<Image> images = new ArrayList<>();
        while (resultSet.next()) {
            Image img = new Image();
            img.setId(resultSet.getInt("image_id"));
            img.setName(resultSet.getString("name"));
            img.setFile(resultSet.getString("file"));
            img.setIs_cover(resultSet.getBoolean("is_cover"));
            images.add(img);
        }
        return images;
    }
}
