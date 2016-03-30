package dao;

import entities.Receipt;
import entities.ReceiptDto;

import java.sql.SQLException;
import java.util.List;

/**
 * ReceiptDto interaction with a database
 */
public interface ReceiptDao {

    /**
     * Create a new ReceiptDto
     *
     * @param receiptDto
     * @throws SQLException
     */
    void create(Receipt receiptDto) throws DaoException;

    /**
     * Retrieves all receipts with their corresponding receipt entries
     *
     * @return
     * @throws SQLException
     */
    List<Receipt> getAll() throws DaoException;
}
