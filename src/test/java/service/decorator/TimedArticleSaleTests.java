package service.decorator;

import base.BaseTest;
import entities.Article;
import entities.Receipt;
import org.junit.Test;
import org.mockito.Mock;
import service.ReceiptRepository;
import service.criteria.DateOperator;
import sun.java2d.pipe.SpanShapeRenderer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static base.DummyEntityFactory.createDummyArticles;
import static base.DummyEntityFactory.createDummyReceipt;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

public class TimedArticleSaleTests extends BaseTest{

    @Mock
    ReceiptRepository mockReceiptRepository;

    @Test
    public void getTimesSold_AfterCertainDate_TimesSoldReturned() throws Exception {
        List<Article> articles = createDummyArticles(1);
        Receipt earlyReceipt = createDummyReceipt("01/01/2001",articles);
        Receipt laterReceipt = createDummyReceipt("01/01/2004",articles);
        Receipt evenLaterReceipt = createDummyReceipt("01/01/2007",articles);
        List<Receipt> receipts = new ArrayList<>();
        receipts.add(earlyReceipt);
        receipts.add(laterReceipt);
        receipts.add(evenLaterReceipt);
        when(mockReceiptRepository.getAll()).thenReturn(receipts);
        int expectedTimesSold = 1;

        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2006");
        TimedArticleSale articleSale = new TimedArticleSale(mockReceiptRepository,articles.get(0),date, DateOperator.AFTER);
        int timesSold = articleSale.getTimesSold();

        assertEquals(expectedTimesSold,timesSold);
    }

    @Test
    public void getTimesSold_BeforeCertainDate_TimesSoldReturned() throws Exception {
        List<Article> articles = createDummyArticles(1);
        Receipt earlyReceipt = createDummyReceipt("01/01/2001",articles);
        Receipt laterReceipt = createDummyReceipt("01/01/2004",articles);
        Receipt evenLaterReceipt = createDummyReceipt("01/01/2007",articles);
        List<Receipt> receipts = new ArrayList<>();
        receipts.add(earlyReceipt);
        receipts.add(laterReceipt);
        receipts.add(evenLaterReceipt);
        when(mockReceiptRepository.getAll()).thenReturn(receipts);
        int expectedTimesSold = 1;

        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2003");
        TimedArticleSale articleSale = new TimedArticleSale(mockReceiptRepository,articles.get(0),date, DateOperator.BEFORE);
        int timesSold = articleSale.getTimesSold();

        assertEquals(expectedTimesSold,timesSold);
    }
}
