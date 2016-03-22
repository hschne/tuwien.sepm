package dao.h2;

import dao.DaoException;
import dao.ReceiptDao;
import entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2ReceiptDao extends AbstractH2Dao implements ReceiptDao {

    private final Connection connection;

    public H2ReceiptDao(H2Database h2Database) {
        this.connection = h2Database.getConnection();
    }

    @Override
    public void create(Receipt receiptDto) throws DaoException {
        logger.debug("Creating receiptDto " + receiptDto);
        try {
            insertReceiptData(receiptDto);
            insertLinkedArticles(receiptDto);
        } catch (SQLException e) {
            handle(e);
        }
    }

    @Override
    public List<Receipt> readAll() throws DaoException {
        logger.debug("Reading all receipts");
        String query = "SELECT * FROM RECEIPT";
        ResultSet resultSet;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return parseReceipts(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    private List<Receipt> parseReceipts(ResultSet resultSet) throws SQLException {
        List<Receipt> receiptDtos = new ArrayList<>();
        while (resultSet.next()) {
            receiptDtos.add(parseReceipt(resultSet));
        }
        return receiptDtos;
    }

    private ReceiptDto parseReceipt(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        java.util.Date date = new java.util.Date(resultSet.getDate(2).getTime());
        String receiver = resultSet.getString(3);
        String receiverAdress = resultSet.getString(4);
        ReceiptDto receiptDto = new ReceiptDto(id, date,receiver,receiverAdress,null);
        receiptDto.setReceiptEntries(readReceiptEntries(receiptDto));
        return receiptDto;

    }


    private List<ReceiptEntry> readReceiptEntries(ReceiptDto receiptDto) throws SQLException {
        logger.debug("Reading receiptDto entries for " + receiptDto.getId());
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        String query = "SELECT a.ID,a.NAME,a.PRICE,a.DESCRIPTION,a.IMAGE_PATH,a.CATEGORY, rec.AMOUNT " +
                "FROM ARTICLE a , (SELECT * FROM ARTICLE_RECEIPT WHERE RECEIPT =?) rec WHERE rec.ARTICLE = a.ID;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, receiptDto.getId());
        //The result is a join of articles and article_receipts, where all data required by ReceiptEntryDto is given
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            ReceiptEntryDto receiptEntryDto = parseReceiptEntry(receiptDto, resultSet);
            receiptEntries.add(receiptEntryDto);
        }
        return receiptEntries;
    }

    private ReceiptEntryDto parseReceiptEntry(ReceiptDto receiptDto, ResultSet resultSet) throws SQLException {
        int amount = resultSet.getInt(7);
        ArticleDto articleDto = parseArticle(resultSet);
        return new ReceiptEntryDto(receiptDto, articleDto, amount);
    }

    private void insertReceiptData(Receipt receiptDto) throws SQLException {
        String query = "INSERT INTO RECEIPT  (id, CREATION_DATE, RECEIVER,RECEIVER_ADRESS) VALUES " +
                "(default, ?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDate(1, new Date(receiptDto.getDate().getTime()));
        statement.setString(2, receiptDto.getReceiver());
        statement.setString(3, receiptDto.getReceiverAddress());
        statement.executeUpdate();
        ResultSet result = statement.getGeneratedKeys();
        if (result.next()) {
            receiptDto.setId(result.getInt(1));
        } else {
            throw new SQLException("No id created for " + receiptDto.toString());
        }
    }

    private void insertLinkedArticles(Receipt receiptDto) throws SQLException {
        String query = "INSERT INTO ARTICLE_RECEIPT (RECEIPT, ARTICLE, AMOUNT) VALUES (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        for (ReceiptEntry receiptEntryDto : receiptDto.getReceiptEntries()) {
            ArticleDto articleDto = (ArticleDto) receiptEntryDto.getArticle();
            ReceiptDto castReceiptDto = (ReceiptDto) receiptDto;
            statement.setInt(1, castReceiptDto.getId());
            statement.setInt(2, articleDto.getId());
            statement.setInt(3, receiptEntryDto.getAmount());
            statement.addBatch();
        }
        statement.executeBatch();
    }

    private void handle(SQLException e) throws DaoException {
        logger.error(e);
        throw new DaoException(e);
    }


}
