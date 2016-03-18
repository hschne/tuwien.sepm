package dao.h2;

import dao.Database;
import entities.Article;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Base class for h2 DAOs
 */
abstract class AbstractH2Dao {

    protected final Logger logger = LogManager.getLogger(Database.class);

    protected Article parseArticle(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String name = resultSet.getString(2);
        double price = resultSet.getDouble(3);
        String description = resultSet.getString(4);
        String image = resultSet.getString(5);
        String category = resultSet.getString(6);
        return new Article(id, name, price, description, image, category);
    }
}
