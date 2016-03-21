package service.criteria;

import entities.Article;
import entities.Receipt;
import entities.ReceiptEntry;
import service.ReceiptRepository;
import service.ServiceException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TimesSoldCriteria implements Criteria<Article> {

    private final int times;
    private final Operator operator;
    private Date date;
    private DateOperator dateOperator;
    private ReceiptRepository repository;


    public TimesSoldCriteria(ReceiptRepository repository, int times, Operator operator, Date date, DateOperator dateOperator) {
        this.repository = repository;
        this.times = times;
        this.operator = operator;
        this.date = date;
        this.dateOperator = dateOperator;
    }

    @Override
    public List<Article> apply(List<Article> list) throws ServiceException {
        List<Article> result = new ArrayList<>();
        for (Article article : list) {
            if (articleMatches(article)) {
                result.add(article);
            }

        }
        return result;
    }

    private boolean articleMatches(Article article) throws ServiceException {
        switch (operator) {
            case GREATER:
                return getTimesSold(article) > times;
            case GREATER_EQUALS:
                return getTimesSold(article) >= times;
            case LOWER:
                return getTimesSold(article) < times;
            case LOWER_EQUALS:
                return getTimesSold(article) <= times;
            case EQUALS:
                return getTimesSold(article) == times;
            default:
                throw new ServiceException("Unsupported Operator");
        }
    }

    private int getTimesSold(Article article) throws ServiceException {
        List<Receipt> receipts = repository.getAll();
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        for (Receipt receipt : receipts) {
            receiptEntries.addAll(receipt.getReceiptEntries());
        }
        List<ReceiptEntry> entriesForArticle = receiptEntries.stream()
                .filter(p -> p.getArticle().equals(article)).collect(Collectors.toList());
        if (date != null) {
            return filterOnDate(entriesForArticle).size();
        }
        return entriesForArticle.size();
    }

    private List<ReceiptEntry> filterOnDate(List<ReceiptEntry> receiptEntries) throws ServiceException {
        List<ReceiptEntry> entries = new ArrayList<>();
        for (ReceiptEntry entry : receiptEntries) {
            if (entryMatches(entry)) {
                entries.add(entry);
            }
        }
        return entries;

    }

    private boolean entryMatches(ReceiptEntry entry) throws ServiceException {
        switch (dateOperator) {
            case BEFORE:
                return getEntryDate(entry).before(date);
            default:
                throw new ServiceException("No valid operation");
        }
    }

    private Date getEntryDate(ReceiptEntry entry) throws ServiceException {
        return entry.getReceipt().getDate();
    }


}
