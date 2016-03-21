package service;

import dao.DaoException;
import dao.ReceiptDao;
import entities.Receipt;

import java.util.List;
import java.util.stream.Collectors;

public class ReceiptRepository extends AbstractService  {

    private ReceiptDao dao;

    private List<Receipt> receipts;

    public ReceiptRepository(ReceiptDao dao) {
        this.dao = dao;
    }

    public List<Receipt> getAll() throws ServiceException {
        logger.debug("Getting all receipts");
        if (receipts == null) {
            try {
                receipts = dao.readAll();
            } catch (DaoException e) {
                logger.error("Receipt retrieval from database has failed", e);
                throw new ServiceException("Could not retrieve receipts", e);
            }
        }
        return receipts;
    }

}
