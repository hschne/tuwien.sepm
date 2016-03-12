package dao;

import entities.Article;
import entities.Receipt;
import entities.ReceiptEntry;

import java.sql.*;
import java.util.List;

public class H2ReceiptDao implements ReceiptDao {

    private final Connection connection;

    public H2ReceiptDao(Database database) {
        this.connection = database.getConnection();
    }


    public void create(Receipt receipt) throws SQLException {
        insertReceiptData(receipt);
        insertLinkedArticles(receipt);
    }

    public List<Receipt> readAll() {
        return null;
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
        }
        else{
            throw new SQLException("No id created for " +receipt.toString());
        }
    }

    private void insertLinkedArticles(Receipt receipt) throws SQLException {
        String query = "INSERT INTO ARTICLE_RECEIPT (RECEIPT, ARTICLE, AMOUNT) VALUES (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        for (ReceiptEntry receiptEntry : receipt.getReceiptEntries()) {
            Article article = receiptEntry.getArticle();
            statement.setInt(1, receipt.getId());
            statement.setInt(2, article.getId());
            statement.setInt(3, receiptEntry.getQuantity());
            statement.addBatch();
        }
        statement.executeBatch();
    }


}
