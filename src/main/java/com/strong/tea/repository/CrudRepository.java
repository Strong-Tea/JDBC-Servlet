package com.strong.tea.repository;

import java.sql.SQLException;
import java.util.List;

public interface CrudRepository<T, ID> {
    T findById(ID id) throws SQLException;
    List<T> findAll() throws SQLException;
    T update(T entity) throws SQLException;
    boolean deleteById(ID id) throws SQLException;
    T save(T entity) throws SQLException;
}
