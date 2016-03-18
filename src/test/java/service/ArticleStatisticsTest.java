package service;

import base.BaseTest;
import entities.Article;
import entities.Receipt;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static service.DummyObjectFactory.createDummyArticles;
import static service.DummyObjectFactory.createDummyReceipt;
import static service.DummyObjectFactory.createDummyReceipts;

public class ArticleStatisticsTest extends BaseTest{

    @Mock
    private ReceiptRepository mockRepository;

    @Test
    public void timesSold_SingleArticle_CalculatesTimesSold() throws Exception {
        List<Article> dummyArticles = createDummyArticles(1);
        List<Receipt> dummyReceipts = new ArrayList<Receipt>();
        dummyReceipts.add(createDummyReceipt(dummyArticles));
        dummyReceipts.add(createDummyReceipt(dummyArticles));
        dummyReceipts.add(createDummyReceipt(dummyArticles));
        int expectedTimesSold = 3;

        when(mockRepository.getReceipts()).thenReturn(dummyReceipts);
        ArticleStatistics statistics = new ArticleStatistics(mockRepository, dummyArticles);

        List<StatisticEntry> statisticEntries = statistics.timesSold();

        assertEquals(expectedTimesSold, statisticEntries.get(0).getTotalTimesSold());

    }
}