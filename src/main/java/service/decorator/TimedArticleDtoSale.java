package service.decorator;

import entities.Article;
import entities.ArticleDto;
import entities.ReceiptEntry;
import entities.ReceiptEntryDto;
import service.ReceiptRepository;
import service.ServiceException;
import service.criteria.DateOperator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimedArticleDtoSale extends ArticleDtoSale {

    private final Date date;
    private final DateOperator operator;

    TimedArticleDtoSale(ReceiptRepository repository, Article articleDto, Date date, DateOperator operator){
        super(repository, articleDto);
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
            case AFTER:
                return getEntryDate(entry).after(date);
            default:
                throw new ServiceException("No valid operation");
        }
    }

    private Date getEntryDate(ReceiptEntry entry) throws ServiceException {
        return entry.getReceipt().getDate();
    }


}
