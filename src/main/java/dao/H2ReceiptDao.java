package dao;

import entities.Article;
import entities.Receipt;
import entities.ReceiptEntry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2ReceiptDao extends AbstractH2Dao implements ReceiptDao {

    private final Connection connection;

    public H2ReceiptDao(Database database) {
        this.connection = database.getConnection();
    }


    public void create(Receipt receipt) throws SQLException {
        insertReceiptData(receipt);
        insertLinkedArticles(receipt);
    }

    public List<Receipt> readAll() throws SQLException {
        String query = "SELECT * FROM RECEIPT";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        return parseReceipts(resultSet);
    }

    private List<Receipt> parseReceipts(ResultSet resultSet) throws SQLException {
        List<Receipt> receipts = new ArrayList<Receipt>();
        while (resultSet.next()) {
            receipts.add(parseReceipt(resultSet));
        }
        return receipts;
    }

    private Receipt parseReceipt(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        java.util.Date date = new java.util.Date(resultSet.getDate(2).getTime());
        String receiver = resultSet.getString(3);
        String receiverAdress = resultSet.getString(4);
        Receipt receipt = new Receipt(id, date, receiver, receiverAdress, readReceiptEntries(id));
        return receipt;

    }

    private List<ReceiptEntry> readReceiptEntries(int id) throws SQLException {
        List<ReceiptEntry> receiptEntries = new ArrayList<ReceiptEntry>();
        String query = "SELECT a.ID,a.NAME,a.PRICE,a.DESCRIPTION,a.IMAGE_PATH,a.CATEGORY, rec.AMOUNT " +
                "FROM ARTICLE a , (SELECT * FROM ARTICLE_RECEIPT WHERE RECEIPT =?) rec WHERE rec.ARTICLE = a.ID;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            receiptEntries.add(parseReceiptEntry(resultSet));
        }
        return receiptEntries;
    }

    private ReceiptEntry parseReceiptEntry(ResultSet resultSet) throws SQLException {
        int amount = resultSet.getInt(7);
        Article article = parseArticle(resultSet);
        return new ReceiptEntry(article,amount);
    }

    private void insertReceiptData(Receipt receipt) throws SQLException {
        String query = "INSERT INTO RECEIPT  (id, CREATION_DATE, RECEIVER,RECEIVER_ADRESS) VALUES " +
                "(default, ?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDate(1, new Date(receipt.getDate().getTime()));
        statement.setString(2, receipt.getReceiver());
        statement.setString(3, receipt.getReceiverAddress());
        statement.executeUpdate();
        ResultSet result = statement.getGeneratedKeys();
        if (result.next()) {
            receipt.setId(result.getInt(1));
        } else {
            throw new SQLException("No id created for " + receipt.toString());
        }
    }

    private void insertLinkedArticles(Receipt receipt) throws SQLException {
        String query = "INSERT INTO ARTICLE_RECEIPT (RECEIPT, ARTICLE, AMOUNT) VALUES (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        for (ReceiptEntry receiptEntry : receipt.getReceiptEntries()) {
            Article article = receiptEntry.getArticle();
            statement.setInt(1, receipt.getId());
            statement.setInt(2, article.getId());
            statement.setInt(3, receiptEntry.getAmount());
            statement.addBatch();
        }
        statement.executeBatch();
    }


}
