package service;

import base.BaseTest;
import dao.DaoException;
import dao.ReceiptDao;
import entities.Receipt;
import entities.ReceiptDto;
import org.junit.Test;
import org.mockito.Mock;
import service.criteria.Criteria;

import java.util.List;

import static base.DummyEntityFactory.createDummyReceipt;
import static base.DummyEntityFactory.createDummyReceipts;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReceiptRepositoryTests extends BaseTest {

    @Mock
    ReceiptDao mockReceiptDao;

    @Mock
    Criteria<Receipt> mockReceiptCriteria;

    @Test
    public void getAll_GetReceiptsFromDao_ReceiptReturned() throws Exception {
        List<Receipt> receiptsToReturn = createDummyReceipts(5);
        ReceiptRepository repository = new ReceiptRepository(mockReceiptDao);
        when(mockReceiptDao.getAll()).thenReturn(receiptsToReturn);

        List<Receipt> receiptDtos = repository.getAll();

        assertEquals(receiptsToReturn, receiptDtos);
    }

    @Test(expected = ServiceException.class)
    public void getAll_DaoErrorOccurs_ServiceExceptionThrown() throws Exception {
        ReceiptRepository repository = new ReceiptRepository(mockReceiptDao);
        when(mockReceiptDao.getAll()).thenThrow(DaoException.class);

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

    @Test
    public void create_CreateNewArticle_ReceiptDtoCreateCalled() throws Exception {
        List<Receipt> receiptsToReturn = createDummyReceipts(5);
        ReceiptRepository repository = new ReceiptRepository(mockReceiptDao);
        when(mockReceiptDao.getAll()).thenReturn(receiptsToReturn);
        Receipt receipt = createDummyReceipts(1).get(0);

        repository.create(receipt);

        verify(mockReceiptDao).create(receipt);
    }

    @Test
    public void create_CreateNewReceipt_ReceiptAddedToInternalList() throws Exception {
        List<Receipt> receiptList = createDummyReceipts(0);
        ReceiptRepository repository = new ReceiptRepository(mockReceiptDao);
        when(mockReceiptDao.getAll()).thenReturn(receiptList);
        Receipt receipt = createDummyReceipts(1).get(0);

        repository.create(receipt);

        assertTrue(receiptList.contains(receipt));

    }

    @Test(expected = ServiceException.class)
    public void create_DaoErrorOccurs_ServiceExceptionThrown() throws Exception {
        ReceiptRepository repository = new ReceiptRepository(mockReceiptDao);
        doThrow(DaoException.class).when(mockReceiptDao).create(any(Receipt.class));

        repository.create(new ReceiptDto());
    }
}