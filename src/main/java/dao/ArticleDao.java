package dao;

import entities.Article;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDao implements IDao<Article> {

    private final Logger logger = LogManager.getLogger(Database.class);

    private Database database;

    public ArticleDao(Database database) {
        this.database = database;
    }

    public List<Article> readAll() throws SQLException {
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM ARTICLE ORDER BY ID DESC;";
        ResultSet resultSet = statement.executeQuery(query);
        return parseArticles(resultSet);
    }

    public void update(Article article) throws SQLException {
        Connection connection = database.getConnection();
        if (isArticleLinked(article, connection)) {
            makeInvisible(connection, article);
            create(article);
        } else {
            updateArticle(article, connection);
        }
    }

    public void create(Article article) throws SQLException {
        logger.debug("Creating article " + article.toString());
        Connection connection = database.getConnection();
        PreparedStatement statement = getCreateStatement(article, connection);
        statement.executeUpdate();
        logger.debug("Article created");
        ResultSet result = statement.getGeneratedKeys();
        if (result.next()) {
            article.setId(result.getInt(1));
        }
    }

    public void delete(Article entity) throws SQLException {
        Connection connection = database.getConnection();
        if (isArticleLinked(entity, connection)) {
            makeInvisible(connection, entity);
        } else {
            deleteArticle(connection, entity);
        }
    }

    private void updateArticle(Article article, Connection connection) throws SQLException {
        String updateQuery = "UPDATE ARTICLE SET NAME=?, PRICE=?, DESCRIPTION=?, IMAGE=?, CATEGORY=? WHERE ID=?;";
        PreparedStatement statement = connection.prepareStatement(updateQuery);
        statement.setInt(6, article.getId());
        statement.setString(1, article.getName());
        statement.setDouble(2, article.getPrice());
        statement.setString(3, article.getDescription());
        statement.setString(4, article.getImage());
        statement.setString(5, article.getCategory());
        statement.executeUpdate();
    }

    private boolean isArticleLinked(Article entity, Connection connection) throws SQLException {
        String getQuery = "SELECT * FROM ARTICLE_RECEIPT WHERE ARTICLE=?;";
        PreparedStatement pstmt = connection.prepareStatement(getQuery);
        pstmt.setInt(1, entity.getId());
        ResultSet result = pstmt.executeQuery();
        return result.next();
    }

    private PreparedStatement getCreateStatement(Article article, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into article values (default, ?, ?, ?, ?, ?)");
        statement.setString(1, article.getName());
        statement.setDouble(2, article.getPrice());
        statement.setString(3, article.getDescription());
        statement.setString(4, article.getImage());
        statement.setString(5, article.getCategory());
        return statement;
    }

    private List<Article> parseArticles(ResultSet resultSet) throws SQLException {
        List<Article> articles = new ArrayList<Article>();
        while (resultSet.next()) {
            parseArticle(resultSet, articles);
        }
        return articles;
    }

    private void parseArticle(ResultSet resultSet, List<Article> articles) throws SQLException {
        int id = resultSet.getInt(1);
        String name = resultSet.getString(2);
        double price = resultSet.getDouble(3);
        String description = resultSet.getString(4);
        String image = resultSet.getString(5);
        String category = resultSet.getString(6);
        Article article = new Article(id, name, price, description, image, category);
        articles.add(article);
    }

    private void makeInvisible(Connection connection, Article article) throws SQLException {
        String alterQuery = "UPDATE ARTICLE SET VISIBLE=FALSE WHERE ID=?;";
        PreparedStatement pstmt = connection.prepareStatement(alterQuery);
        pstmt.setInt(1, article.getId());
        pstmt.executeUpdate();
    }

    private void deleteArticle(Connection connection, Article article) throws SQLException {
        String deleteQuery = "DELETE FROM ARTICLE WHERE ID =?;";
        PreparedStatement pstmt = connection.prepareStatement(deleteQuery);
        pstmt.setInt(1, article.getId());
        pstmt.executeUpdate();
    }
}
