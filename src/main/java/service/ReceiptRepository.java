package service;

import dao.ReceiptDao;
import entities.Receipt;
import service.filter.DatePredicate;
import service.filter.NumberPredicate;
import service.filter.ReceiptCriteria;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ReceiptRepository {

    private ReceiptDao dao;

    private List<Receipt> receipts;

    public ReceiptRepository(ReceiptDao dao) {
        this.dao = dao;
    }

    public List<Receipt> getReceipts() throws SQLException {
        if (receipts == null) {
            receipts = dao.readAll();
        }
        return receipts;
    }

    public List<Receipt> filter(ReceiptCriteria criteria) throws SQLException {
        List<Receipt> filteredReceipts = getReceipts();
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
