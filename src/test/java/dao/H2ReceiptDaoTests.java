package dao;

import dao.h2.H2ReceiptDao;
import entities.*;
import org.junit.Test;

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
        ReceiptDao dao = new H2ReceiptDao(mockH2Database);
        when(mockResultSet.getInt(anyInt())).thenReturn(1);
        when(mockResultSet.next()).thenReturn(true);
        ReceiptDto receiptDto = new ReceiptDto(1, new Date(), "", "", new ArrayList<>());

        dao.create(receiptDto);

        assertEquals(1, receiptDto.getId());
    }

    @Test
    public void create_NewReceipt_ReceiptInserted() throws Exception {
        ReceiptDao dao = new H2ReceiptDao(mockH2Database);
        Date date = new Date();
        String receiver = "Receiver";
        String receiverAddress = "ReceiverAddress";
        ReceiptDto receiptDto = new ReceiptDto(date, receiver, receiverAddress, new ArrayList<>());
        when(mockResultSet.next()).thenReturn(true);

        dao.create(receiptDto);

        verify(mockStatement).setDate(anyInt(), eq(new java.sql.Date(receiptDto.getDate().getTime())));
        verify(mockStatement).setString(anyInt(), eq(receiver));
        verify(mockStatement).setString(anyInt(), eq(receiverAddress));
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    public void create_NewReceipt_ReceiptEntriesInserted() throws Exception {
        int articleId = 1;
        int receiptId = 1;
        int amount = 1;
        ReceiptDao dao = new H2ReceiptDao(mockH2Database);
        Receipt receiptDto = new ReceiptDto(1, new Date(), "", "", dummyReceiptEntries());
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(anyInt())).thenReturn(1);

        dao.create(receiptDto);

        verify(mockStatement).setInt(1, receiptId);
        verify(mockStatement).setInt(2, articleId);
        verify(mockStatement).setInt(2, amount);
        verify(mockStatement, times(1)).executeBatch();
    }

    @Test(expected = DaoException.class)
    public void create_NewReceipt_ReceiptCreationFailed() throws Exception {
        ReceiptDao dao = new H2ReceiptDao(mockH2Database);
        dao.create(new ReceiptDto(new Date(), "", "", new ArrayList<>()));
        when(mockResultSet.next()).thenReturn(false);
    }

    @Test
    public void readAll_GetAllReceipts_ReceiptReturned() throws Exception {
        int id = 1;
        Date date = new Date(0);
        String receiver = "Receiver";
        String receiverAdress = "ReceiverAdress";
        ReceiptDao dao = new H2ReceiptDao(mockH2Database);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt(1)).thenReturn(id);
        when(mockResultSet.getDate(2)).thenReturn(new java.sql.Date(date.getTime()));
        when(mockResultSet.getString(3)).thenReturn(receiver);
        when(mockResultSet.getString(4)).thenReturn(receiverAdress);

        List<Receipt> receiptDtos = dao.getAll();

        assertEquals(receiptDtos.size(), 1);
        Receipt receiptDto = receiptDtos.get(0);
        assertEquals(id, receiptDto.getId());
        assertEquals(date, receiptDto.getDate());
        assertEquals(receiver, receiptDto.getReceiver());
        assertEquals(receiverAdress, receiptDto.getReceiverAddress());
    }

    @Test
    public void readAll_GetAllReceipts_ReceiptEntriesReturned() throws Exception {
        ReceiptDao dao = new H2ReceiptDao(mockH2Database);
        int receiptId = 1;
        Date receiptDate = new Date();
        int articleId = 1;
        String name = "NameCriteria";
        int articleAmount = 1;
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt(1)).thenReturn(receiptId).thenReturn(articleId);
        when(mockResultSet.getString(2)).thenReturn(name);
        when(mockResultSet.getInt(7)).thenReturn(articleAmount);
        when(mockResultSet.getDate(2)).thenReturn(new java.sql.Date(receiptDate.getTime()));

        List<Receipt> receiptDtos = dao.getAll();

        List<ReceiptEntry> receiptEntries = receiptDtos.get(0).getReceiptEntries();
        assertEquals(receiptEntries.size(), 1);
        ReceiptEntry entry = receiptEntries.get(0);
        assertEquals(articleAmount, entry.getAmount());
        Article articleDto = entry.getArticle();
        assertEquals(articleId, articleDto.getId());
        assertEquals(name, articleDto.getName());
    }

    private List<ReceiptEntry> dummyReceiptEntries() {
        ArticleDto articleDto = new ArticleDto(1, "", 0.0, "", "", "");
        ReceiptEntryDto entry = new ReceiptEntryDto(null, articleDto, 1);
        List<ReceiptEntry> entries = new ArrayList<>();
        entries.add(entry);
        return entries;
    }
}