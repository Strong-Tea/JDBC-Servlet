package com.strong.tea.repository;

import com.strong.tea.database.DatabaseConnection;
import com.strong.tea.database.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * An abstract base class providing basic CRUD (Create, Read, Update, Delete) operations
 * for interacting with a database. It implements the CrudRepository interface.
 * Subclasses are expected to implement specific behavior for each operation.
 *
 * @param <T>  The type of entity managed by the repository.
 * @param <ID> The type of the entity's identifier.
 */
public abstract class AbstractRepo<T, ID> implements CrudRepository<T, ID> {

    private final Class<T> entityClass;

    public AbstractRepo(Class<T> entityClass) {
        this.entityClass = entityClass;
    }


    @Override
    public T findById(ID id) throws SQLException {
        String sql = DatabaseUtils.generateSelectById(entityClass, id);
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Get only one entity
                return DatabaseUtils.mapResultSetToEntity(resultSet, entityClass);
            }
        }
        return null;
    }


    @Override
    public List<T> findAll() throws SQLException {
        String sql = DatabaseUtils.generateSelectAllQuery(entityClass);
        List<T> entities = null;
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();
            entities = new ArrayList<>();
            while (resultSet.next()) {
                T entity = DatabaseUtils.mapResultSetToEntity(resultSet, entityClass);
                entities.add(entity);
            }
        }
        return entities;
    }


    @Override
    public T update(T entity) throws SQLException {
        String sql = DatabaseUtils.generateUpdateQuery(entity);
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.executeUpdate();
        }
        return entity;
    }


    @Override
    public T save(T entity) throws SQLException {
        String sql = DatabaseUtils.generateInsertQuery(entity, false);
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.executeUpdate();
        }
        return entity;
    }


    @Override
    public boolean deleteById(ID id) throws SQLException {
        String sql = DatabaseUtils.generateDeleteById(entityClass, id);
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            return statement.executeUpdate() > 0;
        }
    }
}
