package service;

import base.BaseTest;
import dao.ArticleDao;
import entities.Article;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import service.filter.ArticleCriteria;
import service.filter.NumberPredicate;
import service.filter.Operator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static base.DummyEntityFactory.createDummyArticles;
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

        List<Article> result = repository.getAll();

        assertEquals(expectedArticles, result);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = ServiceException.class)
    public void getArticles_ExceptionOccurs_ExceptionRethrown() throws Exception {
        ArticleRepository repository = new ArticleRepository(mockArticleDao);
        when(mockArticleDao.getVisible()).thenThrow(SQLException.class);

        repository.getAll();
    }

    @Test
    public void filter_EmptyFilter_ReturnsSameList() throws Exception {
        ArticleRepository repository = new ArticleRepository(mockArticleDao);
        List<Article> expectedArticles = new ArrayList<>();
        when(mockArticleDao.getVisible()).thenReturn(expectedArticles);

        List<Article> articles = repository.filter(new ArticleCriteria(null, null, null));

        assertEquals(expectedArticles, articles);
    }

    @Test
    public void filter_ByDouble_ReturnsFilteredResult() throws Exception {
        ArticleRepository repository = new ArticleRepository(mockArticleDao);
        List<Article> expectedArticles = createDummyArticles(5);
        when(mockArticleDao.getVisible()).thenReturn(expectedArticles);

        NumberPredicate criteria = new NumberPredicate(2.0, Operator.GREATER);
        List<Article> articles = repository.filter(new ArticleCriteria(null, null, criteria));

        assertEquals(articles.size(), 2);
    }

    @Test
    public void filter_ByName_ReturnsFilteredResult() throws Exception {
        ArticleRepository repository = new ArticleRepository(mockArticleDao);
        List<Article> expectedArticles = createDummyArticles(5);
        when(mockArticleDao.getVisible()).thenReturn(expectedArticles);

        List<Article> articles = repository.filter(new ArticleCriteria("name0", null, null));

        assertEquals(1, articles.size());

    }

}