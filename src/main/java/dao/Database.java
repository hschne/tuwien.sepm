package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private final Logger logger = LogManager.getLogger(Database.class);

    private Connection connection;

    public Database(String path) throws DaoException {
        this(path, "sa", "sa");
    }

    public Database(String path, String username, String password) throws DaoException {
        logger.debug("Connecting to Database on " +path);
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:" + path, username, password);
        } catch (ClassNotFoundException e) {
            String message = "H2Driver could not be found";
            logger.error(message);
            throw new DaoException(message, e);
        } catch (SQLException e) {
            String message =" Could not connect to database";
            logger.error(message);
            throw new DaoException(message, e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() throws DaoException {
        logger.debug("Disconnecting from Database");
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException("Error disconnecting from database", e);
        }
    }
}
