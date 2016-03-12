package dao;

import entities.IEntity;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T> {

    void create(T entity) throws SQLException;

    List<T> readAll() throws SQLException;

    void update(T entity) throws SQLException;

    void delete(T entity) throws SQLException;

}
