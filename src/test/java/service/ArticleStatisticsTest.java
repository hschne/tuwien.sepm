package service;

import base.BaseTest;
import entities.Article;
import entities.Receipt;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static service.DummyObjectFactory.createDummyArticles;
import static service.DummyObjectFactory.createDummyReceipt;

public class ArticleStatisticsTest extends BaseTest {

    @Mock
    private ReceiptRepository mockRepository;

    @Test
    public void timesSold_SingleArticle_CalculatesTimesSold() throws Exception, CalculationException {
        List<Article> dummyArticles = createDummyArticles(1);
        List<Receipt> dummyReceipts = new ArrayList<>();
        dummyReceipts.add(createDummyReceipt(dummyArticles));
        dummyReceipts.add(createDummyReceipt(dummyArticles));
        dummyReceipts.add(createDummyReceipt(dummyArticles));
        when(mockRepository.getReceipts()).thenReturn(dummyReceipts);
        ArticleStatistics statistics = new ArticleStatistics(mockRepository, dummyArticles);
        int expectedTimesSold = 3;

        List<StatisticEntry> statisticEntries = statistics.timesSold();

        assertEquals(expectedTimesSold, statisticEntries.get(0).getTotalTimesSold());
    }

    @Test
    public void timesSold_MultipleArticles_CalculatesStatForAllArticles() throws Exception, CalculationException {
        List<Article> dummyArticles = createDummyArticles(5);
        List<Receipt> dummyReceipts = new ArrayList<>();
        dummyReceipts.add(createDummyReceipt(dummyArticles));
        dummyReceipts.add(createDummyReceipt(dummyArticles));
        dummyReceipts.add(createDummyReceipt(dummyArticles));
        when(mockRepository.getReceipts()).thenReturn(dummyReceipts);
        ArticleStatistics statistics = new ArticleStatistics(mockRepository, dummyArticles);
        int expectedStatisticsCount = 5;

        List<StatisticEntry> statisticEntries = statistics.timesSold();

        assertEquals(expectedStatisticsCount, statisticEntries.size());
    }

    @SuppressWarnings("unchecked")
    @Test(expected = CalculationException.class)
    public void timesSold_DaoErrorOccurs_RethrowsCalculationExcpeption() throws Exception, CalculationException {
        List<Article> dummyArticles = createDummyArticles(1);
        when(mockRepository.getReceipts()).thenThrow(SQLException.class);
        ArticleStatistics statistics = new ArticleStatistics(mockRepository, dummyArticles);

        statistics.timesSold();
    }
}