package service.criteria;

import base.BaseTest;
import entities.Article;
import org.junit.Test;
import service.criteria.article.NameCriteria;

import java.util.List;

import static base.DummyEntityFactory.createDummyArticles;
import static org.junit.Assert.*;

public class NameCriteriaTest extends BaseTest {

    @Test
    public void testApply_FilterByName_MatchingNameReturned() throws Exception {
        List<Article> articleDtos = createDummyArticles(5);
        String expectedName = "name0";
        Criteria<Article> criteria = new NameCriteria(expectedName);

        List<Article> result = criteria.apply(articleDtos);

        assertEquals(expectedName,result.get(0).getName());
    }
}