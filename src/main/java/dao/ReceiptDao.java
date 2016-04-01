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
     * @throws DaoException Thrown if there is a problem with the database
     */
    void create(Receipt receipt) throws DaoException;

    /**
     * Retrieves all receipts with their corresponding receipt entries
     *
     * @return All available receipts
     * @throws DaoException Thrown if there is a problem with the database
     */
    List<Receipt> getAll() throws DaoException;
}
