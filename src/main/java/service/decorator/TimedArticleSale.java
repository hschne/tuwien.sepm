package service.decorator;

import entities.Article;
import entities.ReceiptEntry;
import service.ReceiptRepository;
import service.ServiceException;
import service.criteria.DateOperator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimedArticleSale extends ArticleSale {

    private final Date date;
    private final DateOperator operator;

    public TimedArticleSale(ReceiptRepository repository, Article article, Date date, DateOperator operator){
        super(repository,article);
        this.date = date;
        this.operator = operator;
    }

    @Override
    public int getTimesSold() throws ServiceException {
        return filterOnDate(entriesForArticle()).size();
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
        switch (operator) {
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