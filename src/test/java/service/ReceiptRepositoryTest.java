package service;

import base.BaseTest;
import dao.DaoException;
import dao.ReceiptDao;
import entities.Article;
import entities.Receipt;
import org.junit.Test;
import org.mockito.Mock;
import service.criteria.Criteria;

import java.util.List;

import static base.DummyEntityFactory.createDummyArticles;
import static base.DummyEntityFactory.createDummyReceipt;
import static base.DummyEntityFactory.createDummyReceipts;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class ReceiptRepositoryTest extends BaseTest {

    @Mock
    ReceiptDao mockReceiptDao;

    @Mock
    Criteria<Receipt> mockReceiptCriteria;

    @Test
    public void getAll_GetReceiptsFromDao_ReceiptReturned() throws Exception {
        List<Receipt> receiptsToReturn = createDummyReceipts(5);
        ReceiptRepository repository = new ReceiptRepository(mockReceiptDao);
        when(mockReceiptDao.readAll()).thenReturn(receiptsToReturn);

        List<Receipt> receipts = repository.getAll();

        assertEquals(receiptsToReturn, receipts);
    }

    @Test(expected = ServiceException.class)
    public void getAll_DaoErrorOccurs_ServiceExceptionThrown() throws Exception {
        ReceiptRepository repository = new ReceiptRepository(mockReceiptDao);
        when(mockReceiptDao.readAll()).thenThrow(DaoException.class);

        repository.getAll();
    }

    @Test
    public void filter_FilterUsingCriteria_ReceiptsReturned() throws Exception {
        List<Receipt> receiptsToReturn = createDummyReceipts(5);
        ReceiptRepository repository = new ReceiptRepository(mockReceiptDao);
        when(mockReceiptCriteria.apply(any(List.class))).thenReturn(receiptsToReturn);

        List<Receipt> articles = repository.filter(mockReceiptCriteria);

        assertEquals(receiptsToReturn, articles);
    }
}