package dao.h2;

import dao.DaoException;
import dao.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Database implements Database {

    private Connection connection;

    public H2Database(String path) throws DaoException {
        this(path, "sa", "sa");
    }

    public H2Database(String path, String username, String password) throws DaoException {
        Logger logger = LogManager.getLogger(H2Database.class);
        logger.debug("Connecting to H2Database on " + path);
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:" + path, username, password);
        } catch (ClassNotFoundException e) {
            String message = "H2Driver could not be found";
            logger.error(message);
            throw new DaoException(message, e);
        } catch (SQLException e) {
            String message = " Could not connect to database";
            logger.error(message);
            throw new DaoException(message, e);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

}
