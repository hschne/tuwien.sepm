package service;

import dao.DaoException;
import dao.ReceiptDao;
import entities.Receipt;
import service.criteria.Criteria;

import java.util.List;

public class ReceiptRepository extends AbstractService implements Repository<Receipt> {

    private ReceiptDao dao;

    private List<Receipt> receipts;

    public ReceiptRepository(ReceiptDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Receipt> getAll() throws ServiceException {
        logger.debug("Getting all receipts");
        if (receipts == null) {
            try {
                receipts = dao.readAll();
            } catch (DaoException e) {
                logger.error("ReceiptDto retrieval from database has failed", e);
                throw new ServiceException("Could not retrieve receipts", e);
            }
        }
        return receipts;
    }

    @Override
    public List<Receipt> filter(Criteria<Receipt> criteria) throws ServiceException {
        return criteria.apply(getAll());
    }

    public void create(Receipt receipt) throws ServiceException {
        try {
            dao.create(receipt);
            receipts.add(receipt);
        } catch (DaoException e) {
            logger.error("Could not create article " + receipt.toString(), e);
            throw new ServiceException("Could not create article", e);
        }
    }


}
