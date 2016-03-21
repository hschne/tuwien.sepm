package service.criteria;

import base.BaseTest;
import entities.Article;
import org.junit.Test;

import java.util.List;

import static base.DummyEntityFactory.createDummyArticles;
import static org.junit.Assert.*;

public class NameCriteriaTest extends BaseTest {

    @Test
    public void testApply_FilterByName_MatchingNameReturned() throws Exception {
        List<Article> articles = createDummyArticles(5);
        String expectedName = "name0";
        Criteria<Article> criteria = new NameCriteria(expectedName);

        List<Article> result = criteria.apply(articles);

        assertEquals(expectedName,result.get(0).getName());
    }
}