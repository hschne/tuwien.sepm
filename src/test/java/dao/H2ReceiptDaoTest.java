package dao;

import entities.Article;
import entities.Receipt;
import entities.ReceiptEntry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class H2ReceiptDaoTest extends DaoTest {

    @Test
    public void create_NewReceipt_ReceiptIdSet() throws Exception {
        ReceiptDao dao = new H2ReceiptDao(mockDatabase);
        when(mockResultSet.getInt(anyInt())).thenReturn(1);
        when(mockResultSet.next()).thenReturn(true);
        Receipt receipt = new Receipt(1, new Date(), "","", new ArrayList<ReceiptEntry>());

        dao.create(receipt);

        assertEquals(1, receipt.getId());
    }

    @Test
    public void create_NewReceipt_DataTransferredToDb() throws Exception {
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

    @Test
    public void testReadAll() throws Exception {

    }

    private List<ReceiptEntry> dummyReceiptEntries() {
        Article article = new Article(1, "", 0.0, "", "", "");
        ReceiptEntry entry = new ReceiptEntry(article, 1);
        List<ReceiptEntry> entries = new ArrayList<ReceiptEntry>();
        entries.add(entry);
        return entries;
    }
}