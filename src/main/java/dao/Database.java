package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private final Logger logger = LogManager.getLogger(Database.class);

    private Connection connection;

    public Database(String path) throws ClassNotFoundException, SQLException {
        this(path, "sa", "sa");
    }

    public Database(String path, String username, String password) throws SQLException, ClassNotFoundException {
        logger.debug("Connecting to Database on " +path);
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:" + path, username, password);
    }

    public Connection getConnection() {
        return connection;
    }

    public void Disconnect() {
        logger.debug("Disconnecting from Database");
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
