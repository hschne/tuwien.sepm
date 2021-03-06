package service.criteria;

import base.BaseTest;
import entities.Article;
import entities.ArticleDto;
import org.junit.Test;
import org.mockito.Mock;
import service.criteria.article.TimesSoldCriteria;
import service.criteria.operator.NumericOperator;
import service.decorator.ArticleSale;
import service.decorator.SaleFactory;

import java.util.List;

import static base.DummyEntityFactory.createDummyArticles;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class TimesSoldCriteriaTest extends BaseTest{

    @Mock
    SaleFactory mockSaleFactory;

    @Mock
    ArticleSale mockArticleSale;

    @Test
    public void testApply_getSoldMoreThan_MatchingReturned() throws Exception {
        List<Article> articleDtos = createDummyArticles(5);
        when(mockSaleFactory.create(any(ArticleDto.class))).thenReturn(mockArticleSale);
        when(mockArticleSale.getTimesSold()).thenReturn(10).thenReturn(1);
        TimesSoldCriteria criteria = new TimesSoldCriteria(mockSaleFactory, 1, NumericOperator.GREATER);

        int expectedSize = 1;
        List<Article> result = criteria.apply(articleDtos);

        assertEquals(expectedSize, result.size());
    }

    @Test
    public void testApply_getSoldLessThan_MatchingReturned() throws Exception {
        List<Article> articleDtos = createDummyArticles(5);
        when(mockSaleFactory.create(any(ArticleDto.class))).thenReturn(mockArticleSale);
        when(mockArticleSale.getTimesSold()).thenReturn(1).thenReturn(10);
        TimesSoldCriteria criteria = new TimesSoldCriteria(mockSaleFactory, 10, NumericOperator.LOWER);

        int expectedSize = 1;
        List<Article> result = criteria.apply(articleDtos);

        assertEquals(expectedSize, result.size());
    }

    @Test
    public void testApply_getSoldExactly_MatchingReturned() throws Exception {
        List<Article> articleDtos = createDummyArticles(5);
        when(mockSaleFactory.create(any(ArticleDto.class))).thenReturn(mockArticleSale);
        when(mockArticleSale.getTimesSold()).thenReturn(1).thenReturn(10);
        TimesSoldCriteria criteria = new TimesSoldCriteria(mockSaleFactory, 1, NumericOperator.EQUALS);

        int expectedSize = 1;
        List<Article> result = criteria.apply(articleDtos);

        assertEquals(expectedSize, result.size());
    }


}