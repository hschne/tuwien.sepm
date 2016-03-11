package dao.tests;

import dao.ArticleDao;
import dao.Database;
import entities.Article;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyInt())).thenReturn(1);

    }

    @After
    public void tearDown(){
        reset(mockDatabase);
        reset(mockConnection);
        reset(mockStatement);
        reset(mockResultSet);
    }
    @Test
    public void CreateArticle_NewArticle_CreateCalledWithParameters() throws Exception {
        ArticleDao dao = new ArticleDao(mockDatabase);
        String name = "Name";
        Double price = 1.0;
        String description = "Description";
        String image = "Image";
        String category = "Category";
        dao.Create(new Article(name, description,image,category,price));

        verify(mockStatement).setString(anyInt(), eq(name));
        verify(mockStatement).setString(anyInt(), eq(description));
        verify(mockStatement).setString(anyInt(), eq(image));
        verify(mockStatement).setString(anyInt(), eq(category));
        verify(mockStatement).setDouble(anyInt(), eq(price));
    }

    @Test
    public void CreateArticle_NewArticle_ArticleIdSet() throws Exception {
        ArticleDao dao = new ArticleDao(mockDatabase);
        Article article = new Article();

        dao.Create(article);

        assertEquals(1, article.getId());
    }


    @Test
    public void testRead() throws Exception {

    }

    @Test
    public void testReadAll() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

}