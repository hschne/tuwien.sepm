package dao;

import entities.Article;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Base class for H2 DAOs
 */
abstract class AbstractH2Dao {

    protected Article parseArticle(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String name = resultSet.getString(2);
        double price = resultSet.getDouble(3);
        String description = resultSet.getString(4);
        String image = resultSet.getString(5);
        String category = resultSet.getString(6);
        Article article = new Article(id, name, price, description, image, category);
        return article;
    }
}
