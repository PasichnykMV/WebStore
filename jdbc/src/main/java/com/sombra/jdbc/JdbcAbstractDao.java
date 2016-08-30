package com.sombra.jdbc;

import com.sombra.dao.AbstractDao;
import com.sombra.dao.PersistException;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.*;
import java.util.Collections;
import java.util.List;

/**
 * Created by Макс on 16.08.2016.
 */
public abstract class JdbcAbstractDao<T extends Serializable> implements AbstractDao<T> {
    protected final static Logger LOGGER = Logger.getLogger(JdbcAbstractDao.class);

    protected Connection getConnection() throws SQLException {
        return JdbcDaoFactory.getInstance().getConnection();
    }

    /**
     * For testing : If DAO from entityClass yet not implemented that return null
     */
    protected <E extends Serializable> E getObjectReferenceById(Class<E> entityClass, Integer id) throws PersistException {
        try {
            JdbcAbstractDao<E> dao = JdbcDaoFactory.getInstance().getDaoByClass(entityClass);
            return dao.getById(id);
        } catch (UnsupportedOperationException e) {
            LOGGER.error("exception in " + this.getClass().getSimpleName(), e);
            return null;
        }
    }

    protected <E extends Serializable> List<E> getObjectReferencesByQuery(Class<E> entityClass, String query) throws PersistException {
        try {
            JdbcAbstractDao<E> dao = JdbcDaoFactory.getInstance().getDaoByClass(entityClass);
            return dao.readListByQuery(query);
        } catch (UnsupportedOperationException e) {
            LOGGER.error("exception in " + this.getClass().getSimpleName(), e);
            return Collections.emptyList();
        }

    }

    abstract protected String getEntityName();

    abstract protected String getReadQuery();

    abstract protected String getCreateQuery();

    abstract protected String getUpdateQuery();

    abstract protected String getDeleteQuery();

    abstract protected String getSelectAllQuery();

    protected String getIdSequenceName() {
        throw new UnsupportedOperationException("The Entity " + getEntityName() + " not have id sequence");
    }

    abstract protected void fillStatementUpdate(T entity, PreparedStatement statement) throws SQLException;

    abstract protected void fillStatementCreate(T entity, PreparedStatement statement) throws SQLException;

    abstract protected List<T> parseResultRow(ResultSet resultSet) throws SQLException, PersistException;

    public boolean executeBySqlQuery(String sql) throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.warn("exception in " + this.getClass().getSimpleName(), e);
            return false;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public T persist(T entity) throws PersistException {

        String sql = getCreateQuery();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            fillStatementCreate(entity, statement);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                return getById(resultSet.getInt(1));
            }
            throw new PersistException();
        } catch (SQLException e) {
            LOGGER.warn("exception in " + this.getClass().getSimpleName(), e);
            throw new PersistException(e);
        } finally {
            closeConnection(connection);
        }
    }

    protected List<T> readListByQuery(String query) throws PersistException {
        List<T> entities = null;
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            entities = parseResultRow(rs);
        } catch (SQLException e) {
            throw new PersistException(e);
        } finally {
            closeConnection(connection);
        }
        return entities;
    }

    protected Integer getNewId() throws PersistException {
        Integer id;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT nextval(\'" + getIdSequenceName() + "\')");
            if (rs.next())
                id = rs.getInt("nextval");
            else throw new PersistException("Can not generate next id with " + getIdSequenceName());
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return id;
    }

    @Override
    public T getById(Integer id) throws PersistException {
        List<T> entities = null;
        String sql = getReadQuery();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            entities = parseResultRow(rs);
        } catch (SQLException e) {
            throw new PersistException(e);
        } finally {
            closeConnection(connection);
        }
        if (entities == null || entities.isEmpty()) {
            throw new PersistException(getEntityName() + " with id " + id + " has not found");
        } else if (entities.size() > 1) {
            throw new PersistException("Found more then one entity with id " + id);
        } else
            return entities.get(0);
    }

    @Override
    public boolean delete(Integer id) throws PersistException {
        int deletes;
        String sql = getDeleteQuery();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            deletes = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("exception in " + this.getClass().getSimpleName(), e);
            throw new PersistException(e);
        } finally {
            closeConnection(connection);
        }

        return deletes > 0;
    }


    public boolean update(T entity) throws PersistException {
        int updates;
        String sql = getUpdateQuery();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            fillStatementUpdate(entity, statement);
            updates = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("exception in " + this.getClass().getSimpleName(), e);
            throw new PersistException(e);
        } finally {
            closeConnection(connection);
        }
        return updates > 0;
    }


    public List<T> getAll() throws PersistException {
        return readListByQuery(getSelectAllQuery());
    }

    public void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.info("exception in close connection");
        }
    }
}