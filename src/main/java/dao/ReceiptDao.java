package dao;

import entities.Receipt;

import java.sql.SQLException;
import java.util.List;

/**
 * Receipt interaction with a database
 */
public interface ReceiptDao {

    /**
     * Create a new Receipt
     *
     * @param receipt
     * @throws SQLException
     */
    void create(Receipt receipt) throws SQLException;

    /**
     * Retrieves all receipts with their corresponding receipt entries
     *
     * @return
     * @throws SQLException
     */
    List<Receipt> readAll() throws SQLException;
}
