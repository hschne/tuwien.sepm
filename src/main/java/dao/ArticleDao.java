package dao;

import entities.Article;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ArticleDao implements IDao<Article>{

    private Database database;

    private final String tableName= "Article";

    public ArticleDao(Database database){
        this.database = database;
    }

    public void Create(Article article) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("insert into article values (default, ?, ?, ?, ?, ?)");
        statement.setString(1,"name");
        statement.setDouble(2, 20.2);
        statement.setString(3, "desc");
        statement.setString(4, "image");
        statement.setString(5, "category");
        statement.executeUpdate();
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
