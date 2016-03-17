package service;

import base.BaseTest;
import dao.ReceiptDao;
import entities.Article;
import entities.Receipt;
import entities.ReceiptEntry;
import org.junit.Test;
import org.mockito.Mock;
import service.filter.DateCriteria;
import service.filter.NumberCriteria;
import service.filter.Operator;
import service.filter.ReceiptCriteria;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ReceiptRepositoryTests extends BaseTest {

    @Mock
    protected ReceiptDao mockReceiptDao;

    @Test
    public void getReceipts() throws Exception {
        ReceiptRepository repository = new ReceiptRepository(mockReceiptDao);
        List<Receipt> expectedReceipts = new ArrayList<>();
        when(mockReceiptDao.readAll()).thenReturn(expectedReceipts);

        List<Receipt> result = repository.getReceipts();

        assertEquals(expectedReceipts, result);
    }

    @Test
    public void filter_ByReceiverName_FilteredResultReturned() throws Exception {
        ReceiptRepository repository = new ReceiptRepository(mockReceiptDao);
        List<Receipt> dummyReceipts = createDummyReceipts();
        when(mockReceiptDao.readAll()).thenReturn(dummyReceipts);

        ReceiptCriteria criteria = new ReceiptCriteria("Receiver0", null, null);
        List<Receipt> result = repository.filter(criteria);

        assertEquals(1, result.size());
    }

    @Test
    public void filter_ByDate_FilteredResultReturned() throws Exception {
        ReceiptRepository repository = new ReceiptRepository(mockReceiptDao);
        List<Receipt> dummyReceipts = createDummyReceipts();
        when(mockReceiptDao.readAll()).thenReturn(dummyReceipts);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        DateCriteria dateCriteria = new DateCriteria(sdf.parse("01/01/2002"), Operator.GREATER);
        ReceiptCriteria criteria = new ReceiptCriteria(null, dateCriteria, null);
        List<Receipt> result = repository.filter(criteria);

        assertEquals(2, result.size());
    }

    @Test
    public void filter_ByArticleCount_FilteredResultReturned() throws Exception {
        ReceiptRepository repository = new ReceiptRepository(mockReceiptDao);
        List<Receipt> dummyReceipts = createDummyReceipts();
        when(mockReceiptDao.readAll()).thenReturn(dummyReceipts);

        NumberCriteria numberCriteria = new NumberCriteria(3, Operator.GREATER);
        ReceiptCriteria criteria = new ReceiptCriteria(null, null, numberCriteria);
        List<Receipt> result = repository.filter(criteria);

        assertEquals(1, result.size());
    }

    private List<Receipt> createDummyReceipts() throws ParseException {
        List<Receipt> receipts = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < 5; i++) {
            Receipt receipt = new Receipt(dateFormat.parse("01/01/200" + i), "Receiver" + i, "ReceiverAdress" + i, createDummyReceiptEntries(i));
            receipts.add(receipt);
        }
        return receipts;
    }

    private List<ReceiptEntry> createDummyReceiptEntries(int i) {
        List<ReceiptEntry> entries = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            Article article = new Article(j, "name" + j, j, "description", "", "category" + j);
            entries.add(new ReceiptEntry(article, 1));
        }
        return entries;
    }
}
