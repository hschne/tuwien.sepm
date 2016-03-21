package dao;

import java.sql.Connection;

public interface Database {

    public Connection getConnection();

    public void disconnect() throws DaoException;
}
