package service.decorator;

import base.BaseTest;
import entities.Article;
import entities.ArticleDto;
import entities.Receipt;
import entities.ReceiptDto;
import org.junit.Test;
import org.mockito.Mock;
import service.ReceiptRepository;
import service.criteria.DateOperator;

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
        List<Article> articleDtos = createDummyArticles(1);
        Receipt earlyReceiptDto = createDummyReceipt("01/01/2001", articleDtos);
        Receipt laterReceiptDto = createDummyReceipt("01/01/2004", articleDtos);
        Receipt evenLaterReceiptDto = createDummyReceipt("01/01/2007", articleDtos);
        List<Receipt> receiptDtos = new ArrayList<>();
        receiptDtos.add(earlyReceiptDto);
        receiptDtos.add(laterReceiptDto);
        receiptDtos.add(evenLaterReceiptDto);
        when(mockReceiptRepository.getAll()).thenReturn(receiptDtos);
        int expectedTimesSold = 1;

        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2006");
        TimedArticleDtoSale articleSale = new TimedArticleDtoSale(mockReceiptRepository, articleDtos.get(0),date, DateOperator.AFTER);
        int timesSold = articleSale.getTimesSold();

        assertEquals(expectedTimesSold,timesSold);
    }

    @Test
    public void getTimesSold_BeforeCertainDate_TimesSoldReturned() throws Exception {
        List<Article> articleDtos = createDummyArticles(1);
        Receipt earlyReceiptDto = createDummyReceipt("01/01/2001", articleDtos);
        Receipt laterReceiptDto = createDummyReceipt("01/01/2004", articleDtos);
        Receipt evenLaterReceiptDto = createDummyReceipt("01/01/2007", articleDtos);
        List<Receipt> receiptDtos = new ArrayList<>();
        receiptDtos.add(earlyReceiptDto);
        receiptDtos.add(laterReceiptDto);
        receiptDtos.add(evenLaterReceiptDto);
        when(mockReceiptRepository.getAll()).thenReturn(receiptDtos);
        int expectedTimesSold = 1;

        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2003");
        TimedArticleDtoSale articleSale = new TimedArticleDtoSale(mockReceiptRepository, articleDtos.get(0),date, DateOperator.BEFORE);
        int timesSold = articleSale.getTimesSold();

        assertEquals(expectedTimesSold,timesSold);
    }
}
