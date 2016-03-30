package dao.h2;

import entities.ArticleDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Base class for h2 DAOs
 */
abstract class AbstractH2Dao {

    protected final Logger logger = LogManager.getLogger(AbstractH2Dao.class);

    protected ArticleDto parseArticle(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String name = resultSet.getString(2);
        double price = resultSet.getDouble(3);
        String description = resultSet.getString(4);
        String image = resultSet.getString(5);
        String category = resultSet.getString(6);
        return new ArticleDto(id, name, price, description, image, category);
    }
}
