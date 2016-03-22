package dao.h2;

import dao.ArticleDao;
import dao.DaoException;
import entities.Article;
import entities.ArticleDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class H2ArticleDao extends AbstractH2Dao implements ArticleDao {

    private Connection connection;

    public H2ArticleDao(H2Database h2Database) {
        this.connection = h2Database.getConnection();
    }

    @Override
    public List<Article> getVisible() throws DaoException {
        logger.debug("Getting all articles");
        String query = "SELECT * FROM ARTICLE WHERE VISIBLE=TRUE ORDER BY ID DESC;";
        ResultSet resultSet;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return parseArticles(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Article articleDto) throws DaoException {
        logger.debug("Updating articleDto " + articleDto.toString());
        try {
            updateOrCreate(articleDto);
        } catch (SQLException e) {
            handle(e);
        }
    }

    @Override
    public void create(Article articleDto) throws DaoException {
        logger.debug("Creating articleDto " + articleDto.toString());
        PreparedStatement statement;
        try {
            statement = getCreateStatement(articleDto);
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                articleDto.setId(result.getInt(1));
            } else {
                throw new DaoException("No id created for " + articleDto.toString());
            }
        } catch (SQLException e) {
            handle(e);
        }
    }

    @Override
    public void delete(Article articleDto) throws DaoException {
        logger.debug("Deleting articleDto " + articleDto.toString());
        try {
            deleteOrMakeInvisible(articleDto);
        } catch (SQLException e) {
            handle(e);
        }
    }

    private void deleteOrMakeInvisible(Article articleDto) throws SQLException {
        if (isArticleLinked(articleDto)) {
            makeInvisible(articleDto);
        } else {
            deleteArticle(articleDto);
        }
    }

    private void updateOrCreate(Article articleDto) throws SQLException, DaoException {
        if (isArticleLinked(articleDto)) {
            makeInvisible(articleDto);
            create(articleDto);
        } else {
            updateArticle(articleDto);
        }
    }

    private List<Article> parseArticles(ResultSet resultSet) throws SQLException {
        List<Article> articleDtos = new ArrayList<>();
        while (resultSet.next()) {
            articleDtos.add(parseArticle(resultSet));
        }
        return articleDtos;
    }

    private void updateArticle(Article articleDto) throws SQLException {
        String updateQuery = "UPDATE ARTICLE SET NAME=?, PRICE=?, DESCRIPTION=?, IMAGE_PATH=?, CATEGORY=? WHERE ID=?;";
        PreparedStatement statement = connection.prepareStatement(updateQuery);
        statement.setInt(6, articleDto.getId());
        statement.setString(1, articleDto.getName());
        statement.setDouble(2, articleDto.getPrice());
        statement.setString(3, articleDto.getDescription());
        statement.setString(4, articleDto.getImage());
        statement.setString(5, articleDto.getCategory());
        statement.executeUpdate();
    }

    private boolean isArticleLinked(Article articleDto) throws SQLException {
        String getQuery = "SELECT * FROM ARTICLE_RECEIPT WHERE ARTICLE=?;";
        PreparedStatement statement = connection.prepareStatement(getQuery);
        statement.setInt(1, articleDto.getId());
        ResultSet result = statement.executeQuery();
        return result.next();
    }

    private PreparedStatement getCreateStatement(Article articleDto) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO article VALUES (default, ?, ?, ?, ?, ?,default)");
        statement.setString(1, articleDto.getName());
        statement.setDouble(2, articleDto.getPrice());
        statement.setString(3, articleDto.getDescription());
        statement.setString(4, articleDto.getImage());
        statement.setString(5, articleDto.getCategory());
        return statement;
    }

    private void makeInvisible(Article articleDto) throws SQLException {
        String alterQuery = "UPDATE ARTICLE SET VISIBLE=FALSE WHERE ID=?;";
        PreparedStatement statement = connection.prepareStatement(alterQuery);
        statement.setInt(1, articleDto.getId());
        statement.executeUpdate();
    }

    private void deleteArticle(Article articleDto) throws SQLException {
        String deleteQuery = "DELETE FROM ARTICLE WHERE ID =?;";
        PreparedStatement statement = connection.prepareStatement(deleteQuery);
        statement.setInt(1, articleDto.getId());
        statement.executeUpdate();
    }

    private void handle(SQLException e) throws DaoException {
        logger.error(e);
        throw new DaoException(e);
    }

}
