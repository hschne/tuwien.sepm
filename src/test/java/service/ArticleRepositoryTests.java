package service;

import base.BaseTest;
import dao.ArticleDao;
import entities.Article;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;


public class ArticleRepositoryTests extends BaseTest {

    @Mock
    protected ArticleDao mockArticleDao;

    @Before
    public void setUp() throws SQLException {

    }

    @After
    public void tearDown() {
        reset(mockArticleDao);
    }

    @Test
    public void getArticles_ExistingArticles_ArticlesReturned() throws Exception {
        ArticleRepository repository = new ArticleRepository(mockArticleDao);
        List<Article> expectedArticles = new ArrayList<>();
        when(mockArticleDao.getVisible()).thenReturn(expectedArticles);


        List<Article> result = repository.getArticles();

        assertEquals(expectedArticles, result);
    }

    @Test(expected = SQLException.class)
    public void getArticles_ExceptionOccurs_ExceptionRethrown() throws Exception {
        ArticleRepository repository = new ArticleRepository(mockArticleDao);
        when(mockArticleDao.getVisible()).thenThrow(SQLException.class);

        repository.getArticles();
    }

    @Test
    public void testFilter() throws Exception {

    }
}