package com.strong.tea.database;

import com.strong.tea.annotation.Column;
import com.strong.tea.annotation.Id;
import com.strong.tea.annotation.Table;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DatabaseUtils {

    private static final String SELECT_QUERY = "SELECT * FROM %s ORDER BY id ASC;";
    /**
     * Generates a SELECT query to retrieve all rows from a table corresponding to the specified entity class.
     *
     * @param entityClass The Class object representing the entity for which the SELECT query is to be generated.
     * @param <T> The type of the entity.
     * @return A dynamically generated SQL SELECT query.
     * @throws SQLException If an error occurs during query generation.
     */
    public static <T> String generateSelectAllQuery(Class<T> entityClass) throws SQLException {
        try {
            T entity = entityClass.getDeclaredConstructor().newInstance();
            return String.format(SELECT_QUERY, DatabaseUtils.getTableName(entity.getClass()));
        }catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new SQLException("Error generate select query", e);
        }
    }


    /**
     * Generates a SELECT query to retrieve a specific row from a table based on the provided ID.
     *
     * @param entityClass The Class object representing the entity for which the SELECT query is to be generated.
     * @param id The ID value used to identify the row to retrieve.
     * @param <T> The type of the entity.
     * @param <ID> The type of the ID.
     * @return A dynamically generated SQL SELECT query.
     * @throws SQLException If an error occurs during query generation.
     */
    public static <T, ID> String generateSelectById(Class<T> entityClass, ID id) throws SQLException {
        try {
            T entity = entityClass.getDeclaredConstructor().newInstance();
            return "SELECT * FROM " + getTableName(entity.getClass()) + " WHERE " + getIdName(entity) + " = " + id;
        }catch (InstantiationException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new SQLException("Error generate select query", e);
        }
    }


    /**
     * Generates a DELETE query to remove a specific row from a table based on the provided ID.
     *
     * @param entityClass The Class object representing the entity for which the DELETE query is to be generated.
     * @param id The ID value used to identify the row to delete.
     * @param <T> The type of the entity.
     * @param <ID> The type of the ID.
     * @return A dynamically generated SQL DELETE query.
     * @throws SQLException If an error occurs during query generation.
     */
    public static <T, ID> String generateDeleteById(Class<T> entityClass, ID id) throws SQLException {
        try {
            T entity = entityClass.getDeclaredConstructor().newInstance();
            return "DELETE FROM " + getTableName(entity.getClass()) + " WHERE " + getIdName(entity) + " = " + id;
        }catch (InstantiationException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new SQLException("Error generate delete query", e);
        }
    }


    /**
     * Generates an SQL UPDATE query to modify a row in the database table corresponding to the provided entity object.
     *
     * @param entity The object representing the entity to be updated.
     * @return A dynamically generated SQL UPDATE query.
     * @throws SQLException If an error occurs during query generation.
     */
    public static String generateUpdateQuery(Object entity) throws SQLException {
        Class<?> entityClass = entity.getClass();
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(getTableName(entityClass)).append(" SET ");
        Field[] fields = entityClass.getDeclaredFields();
        boolean flag = false;
        try {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                if (fields[i].isAnnotationPresent(Id.class)) { continue; }
                if (!fields[i].isAnnotationPresent(Column.class)) { continue; }
                if (i > 0 && flag) {
                    query.append(", ");
                }
                Column columnAnnotation = fields[i].getAnnotation(Column.class);
                if (columnAnnotation != null && !columnAnnotation.name().isEmpty()) {
                    query.append(columnAnnotation.name());
                } else {
                    query.append(fields[i].getName());
                }
                query.append(" = ");
                if (fields[i].getType() == String.class) {
                    query.append("'").append(fields[i].get(entity)).append("'");
                } else {
                    query.append(fields[i].get(entity));
                }
                flag = true;
            }

            query.append(" WHERE ").append(getIdName(entity)).append(" = ").append(getIdValue(entity)).append(";");
            return query.toString();
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new SQLException("Error generate update query", e);
        }
    }


    /**
     * Generates an SQL INSERT query to add a new row to the database table based on the provided entity object.
     *
     * @param entity The object representing the entity to be inserted.
     * @return A dynamically generated SQL INSERT query.
     * @throws SQLException If an error occurs during query generation.
     */
    public static String generateInsertQuery(Object entity, boolean includeIdField) throws SQLException  {
        Class<?> entityClass = entity.getClass();
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(getTableName(entityClass)).append(" (");

        StringBuilder values = new StringBuilder("VALUES (");

        Field[] fields = entityClass.getDeclaredFields();
        boolean firstField = true;
        try {
            for (Field field : fields) {
                field.setAccessible(true);

                if (!includeIdField && field.isAnnotationPresent(Id.class)) { continue; }
                if(!field.isAnnotationPresent(Column.class)) { continue;}

                if (!firstField) {
                    query.append(", ");
                    values.append(", ");
                } else {
                    firstField = false;
                }

                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation != null && !columnAnnotation.name().isEmpty()) {
                    query.append(columnAnnotation.name());
                } else {
                    query.append(field.getName());
                }

                values.append(getFieldValue(field, entity));
            }

            query.append(") ");
            values.append(")");

            return query.append(" ").append(values).append(";").toString();
        } catch (IllegalAccessException e) {
            throw new SQLException("Error generate insert query", e);
        }
    }


    /**
     * Maps the columns of a ResultSet to the fields of an entity object of the specified class.
     *
     * @param resultSet The ResultSet containing the data to map.
     * @param entityClass The Class object representing the entity to map the ResultSet to.
     * @param <T> The type of the entity.
     * @return An instance of the entity class with fields populated from the ResultSet.
     * @throws SQLException If an error occurs while mapping the ResultSet to the entity.
     */
    public static <T> T mapResultSetToEntity(ResultSet resultSet, Class<T> entityClass) throws SQLException {
        try {
            T entity = entityClass.getDeclaredConstructor().newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Field field = getFieldByColumnName(entityClass, columnName);
                if (field != null) {
                    field.setAccessible(true);
                    field.set(entity, resultSet.getObject(i));
                }
            }
            return entity;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new SQLException("Error mapping result set to entity", e);
        }
    }


    /**
     * Retrieves the table name associated with the specified entity class.
     * If the entity class is annotated with @Table, returns the name specified in the annotation.
     * Otherwise, returns the lowercase simple name of the entity class.
     *
     * @param entityClass The Class object representing the entity.
     * @return The name of the database table associated with the entity class.
     */
    private static String getTableName(Class<?> entityClass) {
        Table entityAnnotation = entityClass.getAnnotation(Table.class);
        if (entityAnnotation != null) {
            return entityAnnotation.name();
        }
        return entityClass.getSimpleName();
    }


    /**
     * Retrieves the value of a field from an entity object and converts it to a string representation.
     * If the value is null, returns "NULL".
     * If the value is a string, encloses it in single quotes.
     *
     * @param field The Field object representing the field whose value is to be retrieved.
     * @param entity The object from which to retrieve the field value.
     * @return A string representation of the field value.
     * @throws IllegalAccessException If the field is inaccessible.
     */
    private static String getFieldValue(Field field, Object entity) throws IllegalAccessException {
        Object value = field.get(entity);
        if (value != null) {
            if (value instanceof String) {
                return "'" + value + "'";
            }
            return value.toString();
        }
        return "NULL";
    }


    /**
     * Retrieves the value of the field annotated with @Id from the provided entity object.
     *
     * @param entity The object from which to retrieve the ID value.
     * @return The value of the field annotated with @Id.
     * @throws IllegalAccessException If the field is inaccessible.
     * @throws IllegalArgumentException If no field annotated with @Id is found in the entity class.
     */
    private static Object getIdValue(Object entity) throws IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Id.class)) {
                return field.get(entity);
            }
        }
        throw new IllegalArgumentException("No field annotated with @Id found in entity class.");
    }


    /**
     * Retrieves the field of the specified entity class that corresponds to the given column name.
     *
     * @param entityClass The Class object representing the entity.
     * @param columnName The name of the column to find the corresponding field for.
     * @param <T> The type of the entity.
     * @return The Field object representing the field corresponding to the column name, or null if not found.
     */
    private static <T> Field getFieldByColumnName(Class<T> entityClass, String columnName) {
        for (Field field : entityClass.getDeclaredFields()) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                if (!columnAnnotation.name().isEmpty()) {
                    if (columnAnnotation.name().equals(columnName)) {
                        return field;
                    }
                } else if (field.getName().equals(columnName)) {
                    return field;
                }
            }
        }
        return null;
    }


    /**
     * Retrieves the name of the field annotated with @Id from the provided entity object.
     *
     * @param entity The object from which to retrieve the ID field name.
     * @return The name of the field annotated with @Id.
     * @throws IllegalAccessException If the field is inaccessible.
     * @throws IllegalArgumentException If no field annotated with @Id is found in the entity class.
     */
    private static String getIdName(Object entity) throws IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Id.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation != null && !columnAnnotation.name().isEmpty()) {
                    return columnAnnotation.name();
                } else {
                    return field.getName();
                }
            }
        }
        throw new IllegalArgumentException("No field annotated with @Id found in entity class.");
    }
}