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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ArticleRepositoryTests extends BaseTest {

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
        doThrow(DaoException.class).when(mockArticleDao).getVisible();

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

    @Test
    public void update_UpdateArticle_ArticleDaoUpdateCalled() throws Exception {
        Article article = new ArticleDto();
        ArticleRepository repository = new ArticleRepository(mockArticleDao);

        repository.update(article);

        verify(mockArticleDao).update(article);

    }

    @Test(expected = ServiceException.class)
    public void update_DaoErrorOccurs_ServiceExceptionThrown() throws Exception {
        Article article = new ArticleDto();
        ArticleRepository repository = new ArticleRepository(mockArticleDao);
        doThrow(DaoException.class).when(mockArticleDao).update(any());

        repository.update(article);
    }

    @Test
    public void create_CreateNewArticle_ArticleDaoCreateCalled() throws Exception {
        List<Article> articlesToReturn = createDummyArticles(5);
        ArticleRepository repository = new ArticleRepository(mockArticleDao);
        when(mockArticleDao.getVisible()).thenReturn(articlesToReturn);
        Article article = new ArticleDto();

        repository.create(article);

        verify(mockArticleDao).create(article);
    }

    @Test
    public void create_CreateNewArticle_ArticleAddedToInternalList() throws Exception {
        List<Article> articleList = createDummyArticles(5);
        ArticleRepository repository = new ArticleRepository(mockArticleDao);
        when(mockArticleDao.getVisible()).thenReturn(articleList);
        Article article = new ArticleDto();

        repository.create(article);

        assertTrue(articleList.contains(article));
    }

    @Test(expected = ServiceException.class)
    public void create_DaoErrorOccurs_ServiceExceptionThrown() throws Exception {
        Article article = new ArticleDto();
        ArticleRepository repository = new ArticleRepository(mockArticleDao);
        doThrow(DaoException.class).when(mockArticleDao).create(any());

        repository.create(article);
    }

    @Test
    public void delete_DeleteArticle_ArticleDaoDeleteCalled() throws Exception {
        List<Article> articlesToReturn = createDummyArticles(5);
        ArticleRepository repository = new ArticleRepository(mockArticleDao);
        when(mockArticleDao.getVisible()).thenReturn(articlesToReturn);
        Article article = new ArticleDto();

        repository.delete(article);

        verify(mockArticleDao).delete(article);
    }

    @Test
    public void delete_DeleteArticle_ArticleRemovedFromInternalList() throws Exception {
        List<Article> articleList = createDummyArticles(5);
        ArticleRepository repository = new ArticleRepository(mockArticleDao);
        when(mockArticleDao.getVisible()).thenReturn(articleList);
        Article article = new ArticleDto();

        repository.delete(article);

        assertTrue(!articleList.contains(article));
    }

    @Test(expected = ServiceException.class)
    public void delete_DaoErrorOccurs_ServiceExceptionThrown() throws Exception {
        Article article = new ArticleDto();
        ArticleRepository repository = new ArticleRepository(mockArticleDao);
        doThrow(DaoException.class).when(mockArticleDao).delete(any());

        repository.delete(article);
    }
}