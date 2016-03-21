package service.criteria;

import entities.Article;
import entities.Receipt;
import entities.ReceiptEntry;
import service.ReceiptRepository;
import service.ServiceException;

import java.util.*;
import java.util.stream.Collectors;

public class TimesSoldRelativeCriteria implements Criteria<Article> {

    private final ReceiptRepository repository;
    private final int count;
    private final RelativeOperator relativeOperator;
    private final Date date;
    private final DateOperator dateOperator;

    public TimesSoldRelativeCriteria(ReceiptRepository repository, int count, RelativeOperator relativeOperator, Date date, DateOperator dateOperator) {

        this.repository = repository;
        this.count = count;
        this.relativeOperator = relativeOperator;
        this.date = date;
        this.dateOperator = dateOperator;
    }

    @Override
    public List<Article> apply(List<Article> list) throws ServiceException {
        Collections.sort(list, createCustomComparator());
        return list.subList(0, count);
    }

    private Comparator<Article> createCustomComparator() {
        return (o1, o2) -> {
            try {
                if (relativeOperator == RelativeOperator.TOP) {
                    return getOrder(o1, o2);
                } else {
                    return getOrder(o2, o1);
                }
            } catch (ServiceException e) {
                return 0;
            }
        };
    }

    private int getOrder(Article o1, Article o2) throws ServiceException {
        if (getTimesSold(o1) > getTimesSold(o2)) {
            return -1;
        } else if (getTimesSold(o1) < getTimesSold(o2)) {
            return 1;
        } else return 0;
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
