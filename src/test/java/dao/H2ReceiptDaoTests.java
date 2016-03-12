package dao;

import dao.H2.H2ReceiptDao;
import entities.Article;
import entities.Receipt;
import entities.ReceiptEntry;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class H2ReceiptDaoTests extends DaoTest {

    @Test
    public void create_NewReceipt_ReceiptIdSet() throws Exception {
        ReceiptDao dao = new H2ReceiptDao(mockDatabase);
        when(mockResultSet.getInt(anyInt())).thenReturn(1);
        when(mockResultSet.next()).thenReturn(true);
        Receipt receipt = new Receipt(1, new Date(), "", "", new ArrayList<ReceiptEntry>());

        dao.create(receipt);

        assertEquals(1, receipt.getId());
    }

    @Test
    public void create_NewReceipt_ReceiptInserted() throws Exception {
        ReceiptDao dao = new H2ReceiptDao(mockDatabase);
        Date date = new Date();
        String receiver = "Receiver";
        String receiverAddress = "ReceiverAddress";
        Receipt receipt = new Receipt(date, receiver, receiverAddress, new ArrayList<ReceiptEntry>());
        when(mockResultSet.next()).thenReturn(true);

        dao.create(receipt);

        verify(mockStatement).setDate(anyInt(), eq(new java.sql.Date(receipt.getDate().getTime())));
        verify(mockStatement).setString(anyInt(), eq(receiver));
        verify(mockStatement).setString(anyInt(), eq(receiverAddress));
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    public void create_NewReceipt_ReceiptEntriesInserted() throws Exception {
        int articleId = 1;
        int receiptId = 1;
        int amount = 1;
        ReceiptDao dao = new H2ReceiptDao(mockDatabase);
        Receipt receipt = new Receipt(1, new Date(), "", "", dummyReceiptEntries());
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(anyInt())).thenReturn(1);

        dao.create(receipt);

        verify(mockStatement).setInt(1, receiptId);
        verify(mockStatement).setInt(2, articleId);
        verify(mockStatement).setInt(2, amount);
        verify(mockStatement, times(1)).executeBatch();
    }

    @Test(expected = SQLException.class)
    public void create_NewReceipt_ReceiptCreationFailed() throws Exception {
        ReceiptDao dao = new H2ReceiptDao(mockDatabase);
        dao.create(new Receipt(new Date(), "", "", new ArrayList<ReceiptEntry>()));
        when(mockResultSet.next()).thenReturn(false);
    }

    @Test
    public void readAll_GetAllReceipts_ReceiptReturned() throws Exception {
        int id = 1;
        Date date = new Date(00000);
        String receiver = "Receiver";
        String receiverAdress = "ReceiverAdress";
        ReceiptDao dao = new H2ReceiptDao(mockDatabase);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt(1)).thenReturn(id);
        when(mockResultSet.getDate(2)).thenReturn(new java.sql.Date(date.getTime()));
        when(mockResultSet.getString(3)).thenReturn(receiver);
        when(mockResultSet.getString(4)).thenReturn(receiverAdress);

        List<Receipt> receipts = dao.readAll();

        assertEquals(receipts.size(), 1);
        Receipt receipt = receipts.get(0);
        assertEquals(id, receipt.getId());
        assertEquals(date, receipt.getDate());
        assertEquals(receiver, receipt.getReceiver());
        assertEquals(receiverAdress, receipt.getReceiverAddress());
    }

    @Test
    public void readAll_GetAllReceipts_ReceiptEntriesReturned() throws Exception {
        ReceiptDao dao = new H2ReceiptDao(mockDatabase);
        int receiptId = 1;
        Date receiptDate = new Date();
        int articleId = 1;
        String name = "Name";
        int articleAmount = 1;
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt(1)).thenReturn(receiptId).thenReturn(articleId);
        when(mockResultSet.getString(2)).thenReturn(name);
        when(mockResultSet.getInt(7)).thenReturn(articleAmount);
        when(mockResultSet.getDate(2)).thenReturn(new java.sql.Date(receiptDate.getTime()));

        List<Receipt> receipts = dao.readAll();

        List<ReceiptEntry> receiptEntries = receipts.get(0).getReceiptEntries();
        assertEquals(receiptEntries.size(), 1);
        ReceiptEntry entry = receiptEntries.get(0);
        assertEquals(articleAmount, entry.getAmount());
        Article article = entry.getArticle();
        assertEquals(articleId, article.getId());
        assertEquals(name, article.getName());
    }

    private List<ReceiptEntry> dummyReceiptEntries() {
        Article article = new Article(1, "", 0.0, "", "", "");
        ReceiptEntry entry = new ReceiptEntry(article, 1);
        List<ReceiptEntry> entries = new ArrayList<ReceiptEntry>();
        entries.add(entry);
        return entries;
    }
}