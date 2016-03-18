package service;

import dao.DaoException;
import dao.ReceiptDao;
import entities.Receipt;
import service.filter.DatePredicate;
import service.filter.Filter;
import service.filter.NumberPredicate;
import service.filter.ReceiptCriteria;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ReceiptRepository extends AbstractService implements Repository<Receipt>, Filter<ReceiptCriteria> {

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

    public List<Receipt> filter(ReceiptCriteria criteria) throws ServiceException{
        List<Receipt> filteredReceipts = getAll();
        if (criteria.getReceiver() != null) {
            filteredReceipts = filteredReceipts.stream()
                    .filter(p -> p.getReceiver().equals(criteria.getReceiver())).collect(Collectors.toList());
        }
        if (criteria.getDatePredicate() != null) {
            DatePredicate datePredicate = criteria.getDatePredicate();
            filteredReceipts = filteredReceipts.stream()
                    .filter(p -> datePredicate.compare(p.getDate())).collect(Collectors.toList());
        }
        if (criteria.getArticleCountCriteria() != null) {
            NumberPredicate numberPredicate = criteria.getArticleCountCriteria();
            filteredReceipts = filteredReceipts.stream()
                    .filter(p -> numberPredicate.compare(p.getReceiptEntries().size())).collect(Collectors.toList());
        }
        return filteredReceipts;
    }
}
