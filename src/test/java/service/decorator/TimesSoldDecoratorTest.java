package service.decorator;

import base.BaseTest;
import entities.Article;
import entities.Receipt;
import org.junit.Test;
import org.mockito.Mock;
import service.ReceiptRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static base.DummyEntityFactory.createDummyReceipts;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class TimesSoldDecoratorTest  extends BaseTest{

    @Mock
    ReceiptRepository mockReceiptRepository;

    @Test
    public void getTimesSold_ArticleSoldMultipleTimes_TimesSoldReturned() throws Exception {
        List<Receipt> receipts = createDummyReceipts(5);
        Article article = receipts.get(1).getReceiptEntries().get(0).getArticle();
        TimesSoldDecorator decorator = new TimesSoldDecorator(article, mockReceiptRepository);
        when(mockReceiptRepository.getAll()).thenReturn(receipts);
        int expectedTimesSold = 4;

        int timesSold = decorator.getTimesSold();

        assertEquals(expectedTimesSold, timesSold);
    }

    @Test
    public void getTimesSoldSince_ArticleSoldMultipleTimes_TimesSoldReturned() throws Exception {
        List<Receipt> receipts = createDummyReceipts(5);
        Article article = receipts.get(1).getReceiptEntries().get(0).getArticle();
        TimesSoldDecorator decorator = new TimesSoldDecorator(article, mockReceiptRepository);
        when(mockReceiptRepository.getAll()).thenReturn(receipts);
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2003");
        int expectedTimesSold = 1;

        int timesSold = decorator.getTimesSoldSince(date);

        assertEquals(expectedTimesSold, timesSold);
    }

}