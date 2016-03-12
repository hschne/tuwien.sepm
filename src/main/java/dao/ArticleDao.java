package dao;

import entities.Article;

import java.sql.SQLException;
import java.util.List;

public interface ArticleDao {

    void create(Article entity) throws SQLException;

    List<Article> readAll() throws SQLException;

    void update(Article entity) throws SQLException;

    void delete(Article entity) throws SQLException;


}
