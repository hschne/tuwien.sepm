package dao;

import dao.H2.H2ArticleDao;
import entities.Article;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


public class H2ArticleDaoTests extends DaoTest {

    @Test
    public void create_NewArticle_ArticleInserted() throws Exception {
        H2ArticleDao dao = new H2ArticleDao(mockDatabase);
        String name = "Name";
        Double price = 1.0;
        String description = "Description";
        String image = "Image";
        String category = "Category";
        when(mockResultSet.next()).thenReturn(true);

        dao.create(new Article(name, description, image, category, price));

        verify(mockStatement).setString(anyInt(), eq(name));
        verify(mockStatement).setString(anyInt(), eq(description));
        verify(mockStatement).setString(anyInt(), eq(image));
        verify(mockStatement).setString(anyInt(), eq(category));
        verify(mockStatement).setDouble(anyInt(), eq(price));
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test(expected = SQLException.class)
    public void create_NewArticle_CreationFailed() throws Exception {
        H2ArticleDao dao = new H2ArticleDao(mockDatabase);
        when(mockResultSet.next()).thenReturn(false);

        dao.create(new Article());
    }

    @Test
    public void create_NewArticle_ArticleIdSet() throws Exception {
        H2ArticleDao dao = new H2ArticleDao(mockDatabase);
        Article article = new Article();
        when(mockResultSet.getInt(anyInt())).thenReturn(1);
        when(mockResultSet.next()).thenReturn(true);

        dao.create(article);

        assertEquals(1, article.getId());
    }

    @Test
    public void readAll_ReadExistingArticles_QueryCreated() throws Exception {
        H2ArticleDao dao = new H2ArticleDao(mockDatabase);
        String query = "SELECT * FROM ARTICLE WHERE VISIBLE=TRUE ORDER BY ID DESC;";

        dao.getVisible();

        verify(mockConnection).prepareStatement(eq(query));
        verify(mockStatement).executeQuery();
    }

    @Test
    public void readAll_ReadExistingArticles_ArticlesCreated() throws Exception {
        H2ArticleDao dao = new H2ArticleDao(mockDatabase);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString(2)).thenReturn("Name");

        List<Article> articles = dao.getVisible();

        assertEquals(1, articles.size());
        Article article = articles.get(0);
        assertEquals("Name", article.getName());
    }

    @Test
    public void update_ArticleInReceipt_NewArticleCreated() throws Exception {
        H2ArticleDao dao = Mockito.spy(new H2ArticleDao(mockDatabase));
        Article article = new Article(1, "Name", 20.0, "Description", "Image", "Category");
        String query = "UPDATE ARTICLE SET VISIBLE=FALSE WHERE ID=?;";
        when(mockResultSet.next()).thenReturn(true);

        dao.update(article);

        verify(mockConnection).prepareStatement(query);
        verify(dao).create(article);
    }

    @Test
    public void update_ArticleNotInReceipt_ArticleUpdated() throws Exception {
        H2ArticleDao dao = new H2ArticleDao(mockDatabase);
        Article article = new Article(1, "Name", 20.0, "Description", "Image", "Category");
        String query = "UPDATE ARTICLE SET NAME=?, PRICE=?, DESCRIPTION=?, IMAGE_PATH=?, CATEGORY=? WHERE ID=?;";
        when(mockResultSet.next()).thenReturn(false);

        dao.update(article);

        verify(mockConnection).prepareStatement(query);
        verify(mockStatement).executeUpdate();

    }

    @Test
    public void delete_ArticleInReceipt_ArticleAltered() throws Exception {
        H2ArticleDao dao = new H2ArticleDao(mockDatabase);
        Article article = new Article();
        String query = "UPDATE ARTICLE SET VISIBLE=FALSE WHERE ID=?;";
        article.setId(1);
        when(mockResultSet.next()).thenReturn(true);

        dao.delete(article);

        verify(mockConnection).prepareStatement(query);
    }

    @Test
    public void delete_NoLinkedReceipt_ArticleDeleted() throws Exception {
        H2ArticleDao dao = new H2ArticleDao(mockDatabase);
        Article article = new Article();
        String query = "DELETE FROM ARTICLE WHERE ID =?;";
        article.setId(1);
        when(mockResultSet.next()).thenReturn(false);

        dao.delete(article);

        verify(mockConnection).prepareStatement(query);
    }

}