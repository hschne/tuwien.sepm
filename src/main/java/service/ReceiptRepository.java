package service;

import dao.ReceiptDao;
import entities.Receipt;
import service.filter.DateCriteria;
import service.filter.NumberCriteria;
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
        if (criteria.getDateCriteria() != null) {
            DateCriteria dateCriteria = criteria.getDateCriteria();
            filteredReceipts = filteredReceipts.stream()
                    .filter(p -> dateCriteria.compare(p.getDate())).collect(Collectors.toList());
        }
        if (criteria.getArticleCountCriteria() != null) {
            NumberCriteria numberCriteria = criteria.getArticleCountCriteria();
            filteredReceipts = filteredReceipts.stream()
                    .filter(p -> numberCriteria.compare(p.getReceiptEntries().size())).collect(Collectors.toList());
        }
        return filteredReceipts;

    }
}
