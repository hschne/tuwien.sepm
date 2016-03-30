package dao;

import java.sql.Connection;

/**
Interface for database wrappers
 **/
@FunctionalInterface
public interface Database {

    Connection getConnection();

}
