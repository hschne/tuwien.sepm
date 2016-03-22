package service;

import base.BaseTest;
import dao.ArticleDao;
import dao.DaoException;
import entities.Article;
import entities.ArticleDto;
import org.junit.Test;
import org.mockito.Mock;
import service.criteria.Criteria;

import java.util.List;

import static base.DummyEntityFactory.createDummyArticles;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class ArticleDtoRepositoryTest extends BaseTest {

    @Mock
    ArticleDao mockArticleDao;

    @Mock
    Criteria<Article> mockArticleCriteria;

    @Test
    public void getAll_GetArticlesFromDao_ArticlesReturned() throws Exception {
        List<Article> articlesToReturn = createDummyArticles(5);
        ArticleRepository repository = new ArticleRepository(mockArticleDao);
        when(mockArticleDao.getVisible()).thenReturn(articlesToReturn);

        List<Article> articleDtos = repository.getAll();

        assertEquals(articlesToReturn, articleDtos);
    }

    @Test(expected = ServiceException.class)
    public void getAll_DaoErrorOccurs_ServiceExceptionThrown() throws Exception {
        ArticleRepository repository = new ArticleRepository(mockArticleDao);
        when(mockArticleDao.getVisible()).thenThrow(DaoException.class);

        repository.getAll();
    }


    @Test
    public void filter_FilterUsingCriteria_ArticlesReturned() throws Exception {
        List<Article> articlesToReturn = createDummyArticles(5);
        ArticleRepository repository = new ArticleRepository(mockArticleDao);
        when(mockArticleCriteria.apply(any(List.class))).thenReturn(articlesToReturn);

        List<Article> articleDtos = repository.filter(mockArticleCriteria);

        assertEquals(articlesToReturn, articleDtos);
    }
}