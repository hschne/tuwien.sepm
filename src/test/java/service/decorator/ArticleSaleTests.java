package service.decorator;

import base.BaseTest;
import entities.Article;
import entities.Receipt;
import org.junit.Test;
import org.mockito.Mock;
import service.ReceiptRepository;

import java.util.ArrayList;
import java.util.List;

import static base.DummyEntityFactory.createDummyArticles;
import static base.DummyEntityFactory.createDummyReceipt;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

public class ArticleSaleTests extends BaseTest{

    @Mock
    ReceiptRepository mockReceiptRepository;

    @Test
    public void getTimesSold_GetTimesSoldForArticle_TimesSoldReturned() throws Exception {
        List<Article> articles = createDummyArticles(1);
        Receipt receipt = createDummyReceipt(articles);
        List<Receipt> receipts = new ArrayList<>();
        receipts.add(receipt);
        when(mockReceiptRepository.getAll()).thenReturn(receipts);
        int expectedTimesSold = 1;

        int timesSold = new ArticleSale(mockReceiptRepository,articles.get(0)).getTimesSold();

        assertEquals(expectedTimesSold,timesSold);
    }

    @Test
    public void getTimesSold_ArticleNeverSold_ZeroReturned() throws Exception {
        List<Article> articles = createDummyArticles(1);
        List<Receipt> receipts = new ArrayList<>();
        when(mockReceiptRepository.getAll()).thenReturn(receipts);
        int expectedTimesSold = 0;

        int timesSold = new ArticleSale(mockReceiptRepository,articles.get(0)).getTimesSold();

        assertEquals(expectedTimesSold,timesSold);
    }


}
