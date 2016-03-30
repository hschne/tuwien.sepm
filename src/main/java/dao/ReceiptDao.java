package dao;

import entities.Receipt;

import java.util.List;

/**
 * Receipt interaction with a database
 */
public interface ReceiptDao {

    /**
     * Create a new Receipt
     *
     * @param receipt Receipt to create
     * @throws DaoException
     */
    void create(Receipt receipt) throws DaoException;

    /**
     * Retrieves all receipts with their corresponding receipt entries
     *
     * @return All available receipts
     * @throws DaoException
     */
    List<Receipt> getAll() throws DaoException;
}
