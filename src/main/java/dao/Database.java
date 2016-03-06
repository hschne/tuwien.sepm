package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;


public class Database {

    private final Logger logger = LogManager.getLogger(Database.class);

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public Database(String path) throws ClassNotFoundException, SQLException {
         this(path, "sa", "sa");
    }

    public Database(String path, String username, String password) throws SQLException, ClassNotFoundException {
        logger.debug("Connecting to DB...");
        logger.debug("Path: " +path);
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:"+path, username, password);
    }


    public void Disconnect(){
        logger.debug("Disconnecting...");
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
