package dao;

import dao.h2.H2ArticleDao;
import dao.h2.ImageFile;
import entities.Article;
import entities.ArticleDto;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


public class H2ArticleDaoTests extends DaoTest {

    @Mock
    ImageFile mockImageFile;

    @Test
    public void create_NewArticle_ArticleInserted() throws Exception {
        H2ArticleDao dao = new H2ArticleDao(mockH2Database, mockImageFile);
        String name = "NameCriteria";
        Double price = 1.0;
        String description = "Description";
        String image = "Image";
        String category = "Category";
        when(mockResultSet.next()).thenReturn(true);

        dao.create(new ArticleDto(name, description, image, category, price));

        verify(mockStatement).setString(anyInt(), eq(name));
        verify(mockStatement).setString(anyInt(), eq(description));
        verify(mockStatement).setString(anyInt(), eq(category));
        verify(mockStatement).setDouble(anyInt(), eq(price));
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    public void create_NewArticle_ImageSaved() throws Exception {
        H2ArticleDao dao = new H2ArticleDao(mockH2Database, mockImageFile);
        String name = "NameCriteria";
        Double price = 1.0;
        String description = "Description";
        String image = "Image.png";
        String category = "Category";
        when(mockResultSet.next()).thenReturn(true);

        dao.create(new ArticleDto(name, description, image, category, price));

        verify(mockImageFile).add(image);
    }

    @Test(expected = DaoException.class)
    public void create_NewArticle_CreationFailed() throws Exception {
        H2ArticleDao dao = new H2ArticleDao(mockH2Database, mockImageFile);
        when(mockResultSet.next()).thenReturn(false);

        dao.create(new ArticleDto());
    }

    @Test
    public void create_NewArticle_ArticleIdSet() throws Exception {
        H2ArticleDao dao = new H2ArticleDao(mockH2Database, mockImageFile);
        ArticleDto articleDto = new ArticleDto();
        when(mockResultSet.getInt(anyInt())).thenReturn(1);
        when(mockResultSet.next()).thenReturn(true);

        dao.create(articleDto);

        assertEquals(1, articleDto.getId());
    }

    @Test
    public void readAll_ReadExistingArticles_QueryCreated() throws Exception {
        H2ArticleDao dao = new H2ArticleDao(mockH2Database, mockImageFile);
        String query = "SELECT * FROM ARTICLE WHERE VISIBLE=TRUE ORDER BY ID DESC;";

        dao.getVisible();

        verify(mockConnection).prepareStatement(eq(query));
        verify(mockStatement).executeQuery();
    }

    @Test
    public void readAll_ReadExistingArticles_ArticlesCreated() throws Exception {
        H2ArticleDao dao = new H2ArticleDao(mockH2Database, mockImageFile);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString(2)).thenReturn("NameCriteria");

        List<Article> articleDtos = dao.getVisible();

        assertEquals(1, articleDtos.size());
        Article articleDto = articleDtos.get(0);
        assertEquals("NameCriteria", articleDto.getName());
    }

    @Test
    public void update_ArticleInReceipt_NewArticleCreated() throws Exception {
        H2ArticleDao dao = Mockito.spy(new H2ArticleDao(mockH2Database, mockImageFile));
        ArticleDto articleDto = new ArticleDto(1, "NameCriteria", 20.0, "Description", "Image", "Category");
        String query = "UPDATE ARTICLE SET VISIBLE=FALSE WHERE ID=?;";
        when(mockResultSet.next()).thenReturn(true);

        dao.update(articleDto);

        verify(mockConnection).prepareStatement(query);
        verify(dao).create(articleDto);
    }

    @Test
    public void update_ArticleNotInReceipt_ArticleUpdated() throws Exception {
        H2ArticleDao dao = new H2ArticleDao(mockH2Database, mockImageFile);
        ArticleDto articleDto = new ArticleDto(1, "NameCriteria", 20.0, "Description", "Image", "Category");
        
        String query = "UPDATE ARTICLE SET NAME=?, PRICE=?, DESCRIPTION=?, IMAGE_PATH=?, CATEGORY=? WHERE ID=?;";
        when(mockResultSet.next()).thenReturn(false);

        dao.update(articleDto);

        verify(mockConnection).prepareStatement(query);
        verify(mockStatement).executeUpdate();

    }

    @Test
    public void delete_ArticleInReceipt_ArticleAltered() throws Exception {
        H2ArticleDao dao = new H2ArticleDao(mockH2Database, mockImageFile);
        ArticleDto articleDto = new ArticleDto();
        String query = "UPDATE ARTICLE SET VISIBLE=FALSE WHERE ID=?;";
        articleDto.setId(1);
        when(mockResultSet.next()).thenReturn(true);

        dao.delete(articleDto);

        verify(mockConnection).prepareStatement(query);
    }

    @Test
    public void delete_NoLinkedReceipt_ArticleDeleted() throws Exception {
        H2ArticleDao dao = new H2ArticleDao(mockH2Database, mockImageFile);
        ArticleDto articleDto = new ArticleDto();
        String query = "DELETE FROM ARTICLE WHERE ID =?;";
        articleDto.setId(1);
        when(mockResultSet.next()).thenReturn(false);

        dao.delete(articleDto);

        verify(mockConnection).prepareStatement(query);
    }

}