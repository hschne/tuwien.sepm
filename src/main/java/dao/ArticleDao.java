package dao;

import entities.Article;

import java.sql.*;
import java.util.List;

public class ArticleDao implements IDao<Article>{

    private Database database;

    public ArticleDao(Database database){
        this.database = database;
    }

    public void Create(Article article) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("insert into article values (default, ?, ?, ?, ?, ?)");
        statement.setString(1,article.getName());
        statement.setDouble(2, article.getPrice());
        statement.setString(3, article.getDescription());
        statement.setString(4, article.getImage());
        statement.setString(5, article.getCategory());
        statement.executeUpdate();
        ResultSet result = statement.getGeneratedKeys();
        article.setId(result.getInt("Id"));

    }

    public Article Read(int id) {
        return null;
    }

    public List<Article> ReadAll() {
        return null;
    }

    public void Update(Article entity) {

    }

    public void Delete(Article entity) {

    }
}
