package service;

import base.BaseTest;
import dao.ArticleDao;
import entities.ArticleDto;
import org.junit.Test;
import org.mockito.Mock;
import service.calculation.PriceChange;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class PriceChangeTests extends BaseTest {


    @Mock
    private ArticleDao mockArticleDao;

    @Test
    public void raiseByPercent_RaiseBy20_PriceRaised() throws Exception {
        ArticleDto articleDto = new ArticleDto(1, "name", 10.0, "desc", "image", "category");
        List<ArticleDto> articleDtos = new ArrayList<>();
        articleDtos.add(articleDto);
        PriceChange priceChange = new PriceChange(mockArticleDao, articleDtos);
        double expectedPrice = 12.0;

        priceChange.raiseByPercent(20);

        assertEquals(expectedPrice, articleDto.getPrice(), 0);
        verify(mockArticleDao).update(articleDto);
    }

    @Test(expected = ServiceException.class)
    public void raisePercentage_raiseByNegative_ExceptionThrown() throws Exception {
        PriceChange priceChange = new PriceChange(mockArticleDao, new ArrayList<>());
        priceChange.raiseByPercent(-20);

    }

    @Test
    public void reduceByPercent_ReduceBy20_PriceReduced() throws Exception {
        ArticleDto articleDto = new ArticleDto(1, "name", 10.0, "desc", "image", "category");
        List<ArticleDto> articleDtos = new ArrayList<>();
        articleDtos.add(articleDto);
        PriceChange priceChange = new PriceChange(mockArticleDao, articleDtos);
        double expectedPrice = 8.0;

        priceChange.reduceByPercent(20);

        assertEquals(expectedPrice, articleDto.getPrice(), 0);
        verify(mockArticleDao).update(articleDto);
    }

    @Test(expected = ServiceException.class)
    public void reduceByPercentage_DecreaseByTooMuch_ExceptionThrown() throws Exception {
        PriceChange priceChange = new PriceChange(mockArticleDao, new ArrayList<>());
        priceChange.reduceByPercent(100);

    }

    @Test
    public void raiseByAbsolute_RaiseBy5_PriceRaised() throws Exception {
        ArticleDto articleDto = new ArticleDto(1, "name", 10.0, "desc", "image", "category");
        List<ArticleDto> articleDtos = new ArrayList<>();
        articleDtos.add(articleDto);
        PriceChange priceChange = new PriceChange(mockArticleDao, articleDtos);
        double expectedPrice = 15;

        priceChange.raiseByAbsolute(5);

        assertEquals(expectedPrice, articleDto.getPrice(), 0);
        verify(mockArticleDao).update(articleDto);
    }

    @Test
    public void reduceByAbsolute_ReduceBy5_PriceReduced() throws Exception {
        ArticleDto articleDto = new ArticleDto(1, "name", 10.0, "desc", "image", "category");
        List<ArticleDto> articleDtos = new ArrayList<>();
        articleDtos.add(articleDto);
        PriceChange priceChange = new PriceChange(mockArticleDao, articleDtos);
        double expectedPrice = 5;

        priceChange.reduceByAbsolute(5);

        assertEquals(expectedPrice, articleDto.getPrice(), 0);
        verify(mockArticleDao).update(articleDto);
    }

    @Test(expected = ServiceException.class)
    public void reduceByAbsolute_DecreaseByTooMuch_ExceptionThrown() throws Exception {
        ArticleDto articleDto = new ArticleDto(1, "name", 10.0, "desc", "image", "category");
        List<ArticleDto> articleDtos = new ArrayList<>();
        articleDtos.add(articleDto);
        PriceChange priceChange = new PriceChange(mockArticleDao, articleDtos);

        priceChange.reduceByAbsolute(100);
    }


}