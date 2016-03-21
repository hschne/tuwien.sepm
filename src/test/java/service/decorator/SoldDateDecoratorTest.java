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
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SoldDateDecoratorTest extends BaseTest {

    @Mock
    ReceiptRepository mockReceiptRepository;

    @Test
    public void getSoldDates_GetArticleSoldDates_SoldDatesReturned() throws Exception {
        List<Receipt> receipts = createDummyReceipts(5);
        //We want the article that has only been sold on 01/01/2004
        Article article = receipts.get(4).getReceiptEntries().get(3).getArticle();
        SoldDateDecorator decorator = new SoldDateDecorator(article, mockReceiptRepository);
        when(mockReceiptRepository.getAll()).thenReturn(receipts);
        Date expectedDate = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2004");

        List<Date> dates = decorator.getSoldDates();

        assertEquals(expectedDate, dates.get(0));
    }
}