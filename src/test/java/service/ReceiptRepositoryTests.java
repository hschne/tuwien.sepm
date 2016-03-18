package service;

import base.BaseTest;
import dao.ReceiptDao;
import entities.Receipt;
import org.junit.Test;
import org.mockito.Mock;
import service.filter.DateCriteria;
import service.filter.NumberCriteria;
import service.filter.Operator;
import service.filter.ReceiptCriteria;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static service.DummyObjectFactory.createDummyReceipts;

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
        List<Receipt> dummyReceipts = createDummyReceipts(5);
        when(mockReceiptDao.readAll()).thenReturn(dummyReceipts);

        ReceiptCriteria criteria = new ReceiptCriteria("Receiver0", null, null);
        List<Receipt> result = repository.filter(criteria);

        assertEquals(1, result.size());
    }

    @Test
    public void filter_ByDate_FilteredResultReturned() throws Exception {
        ReceiptRepository repository = new ReceiptRepository(mockReceiptDao);
        List<Receipt> dummyReceipts = createDummyReceipts(5);
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
        List<Receipt> dummyReceipts = createDummyReceipts(5);
        when(mockReceiptDao.readAll()).thenReturn(dummyReceipts);

        NumberCriteria numberCriteria = new NumberCriteria(3, Operator.GREATER);
        ReceiptCriteria criteria = new ReceiptCriteria(null, null, numberCriteria);
        List<Receipt> result = repository.filter(criteria);

        assertEquals(1, result.size());
    }

}
