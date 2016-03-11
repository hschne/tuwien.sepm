package dao;

import entities.IEntity;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T> {

    void Create(T entity) throws SQLException;

    List<T> ReadAll() throws SQLException;

    void Update(T entity);

    void Delete(T entity);

}
