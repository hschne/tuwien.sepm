package dao.tests;

import dao.ArticleDao;
import dao.Database;
import entities.Article;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ArticleDaoTest {

    @Mock
    Database mockDatabase;

    @Mock
    Connection mockConnection;

    @Mock
    PreparedStatement mockStatement;

    @Mock
    ResultSet mockResultSet;

    @Before
    public void setUp() throws SQLException {
        when(mockDatabase.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
    }

    @After
    public void tearDown(){
        reset(mockDatabase);
        reset(mockConnection);
        reset(mockStatement);
        reset(mockResultSet);
    }
    @Test
    public void createArticle_NewArticle_CreateCalledWithParameters() throws Exception {
        ArticleDao dao = new ArticleDao(mockDatabase);
        String name = "Name";
        Double price = 1.0;
        String description = "Description";
        String image = "Image";
        String category = "Category";
        dao.create(new Article(name, description,image,category,price));

        verify(mockStatement).setString(anyInt(), eq(name));
        verify(mockStatement).setString(anyInt(), eq(description));
        verify(mockStatement).setString(anyInt(), eq(image));
        verify(mockStatement).setString(anyInt(), eq(category));
        verify(mockStatement).setDouble(anyInt(), eq(price));
    }

    @Test
    public void createArticle_NewArticle_ArticleIdSet() throws Exception {
        ArticleDao dao = new ArticleDao(mockDatabase);
        Article article = new Article();
        when(mockResultSet.getInt(anyInt())).thenReturn(1);
        when(mockResultSet.next()).thenReturn(true);
        dao.create(article);

        assertEquals(1, article.getId());
    }

    @Test
    public void readAll_ReadExistingArticles_QueryCreated() throws Exception {
        ArticleDao dao = new ArticleDao(mockDatabase);
        String query = "SELECT * FROM ARTICLE ORDER BY ID DESC;";
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        dao.readAll();

        verify(mockStatement).executeQuery(eq(query));
    }

    @Test
    public void readAll_ReadExistingArticles_ArticlesCreated() throws Exception {
        ArticleDao dao = new ArticleDao(mockDatabase);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString(2)).thenReturn("Name");

        List<Article> articles = dao.readAll();

        assertEquals(1, articles.size());
        Article article = articles.get(0);
        assertEquals("Name", article.getName());

    }

    @Test
    public void updateArticle_ArticleInReceipt_NewArticleCreated() throws Exception {
        ArticleDao dao = Mockito.spy(new ArticleDao(mockDatabase));
        Article article = new Article(1, "Name", 20.0, "Description", "Image", "Category");
        String query = "UPDATE ARTICLE SET VISIBLE=FALSE WHERE ID=?;";
        when(mockResultSet.next()).thenReturn(true);

        dao.update(article);

        verify(mockConnection).prepareStatement(query);
        verify(dao).create(article);
    }

    @Test
    public void updateArticle_ArticleNotInReceipt_ArticleUpdated() throws Exception {
        ArticleDao dao = new ArticleDao(mockDatabase);
        Article article = new Article(1, "Name", 20.0, "Description", "Image", "Category");
        String query = "UPDATE ARTICLE SET NAME=?, PRICE=?, DESCRIPTION=?, IMAGE=?, CATEGORY=? WHERE ID=?;";
        when(mockResultSet.next()).thenReturn(false);

        dao.update(article);

        verify(mockConnection).prepareStatement(query);
        verify(mockStatement).executeUpdate();

    }

    @Test
    public void deleteArticle_ArticleInReceipt_ArticleAltered() throws Exception {
        ArticleDao dao = new ArticleDao(mockDatabase);
        Article article = new Article();
        String query = "UPDATE ARTICLE SET VISIBLE=FALSE WHERE ID=?;";
        article.setId(1);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);

        dao.delete(article);

        verify(mockConnection).prepareStatement(query);
    }

    @Test
    public void deleteArticle_NoLinkedReceipt_ArticleDeleted() throws Exception {
        ArticleDao dao = new ArticleDao(mockDatabase);
        Article article = new Article();
        String query = "DELETE FROM ARTICLE WHERE ID =?;";
        article.setId(1);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        dao.delete(article);

        verify(mockConnection).prepareStatement(query);
    }

}