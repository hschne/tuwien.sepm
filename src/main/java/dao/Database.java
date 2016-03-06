package dao;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.*;


public class Database {

    private final Logger logger = LogManager.getLogger(Database.class);

    private Connection connection;

    public Database(String path) throws ClassNotFoundException, SQLException {
         this(path, "sa", "sa");
    }

    public Database(String path, String username, String password) throws SQLException, ClassNotFoundException {
        logger.debug("Connecting to DB...");
        logger.debug("Path: " +path);
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:"+path, username, password);
    }

    public void Execute(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
    }

    public void Disconnect() throws SQLException {
        logger.debug("Disconnecting...");
        connection.close();
    }
}
