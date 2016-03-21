package service.criteria;

import base.BaseTest;
import entities.Article;
import org.junit.Test;
import org.mockito.Mock;
import service.decorator.ArticleSale;
import service.decorator.SaleFactory;

import java.util.List;

import static base.DummyEntityFactory.createDummyArticles;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class TimesSoldRelativeCriteriaTest extends BaseTest{

    @Mock
    SaleFactory mockSaleFactory;

    @Mock
    ArticleSale mockArticleSale;

    @Test
    public void apply_GetSingleTopSold_SingleReturned() throws Exception {
        List<Article> articlesToFilter = createDummyArticles(5);
        when(mockSaleFactory.create(any(Article.class))).thenReturn(mockArticleSale);
        when(mockArticleSale.getTimesSold()).thenReturn(10).thenReturn(1);
        TimesSoldRelativeCriteria criteria = new TimesSoldRelativeCriteria(mockSaleFactory, 1, RelativeOperator.TOP);
        int expectedCount = 1;

        List<Article> articles = criteria.apply(articlesToFilter);

        assertEquals(expectedCount,articles.size());
    }

}