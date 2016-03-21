package service.calculation;

import entities.Article;
import service.AbstractService;
import service.ReceiptRepository;
import service.ServiceException;
import service.decorator.TimesSoldDecorator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleStatistic extends AbstractService implements Statistic {

    private ReceiptRepository receiptRepository;

    private List<Article> articles;

    public ArticleStatistic(ReceiptRepository receiptRepository, List<Article> articles) {
        this.receiptRepository = receiptRepository;
        this.articles = articles;
    }

    /**
     * Calculates how many times each article has been sold
     *
     * @return Times sold for each article
     * @throws ServiceException
     */
    public List<StatisticEntry> timesSold() throws ServiceException {
        logger.debug("Calculating times sold");
        List<StatisticEntry> result = new ArrayList<>();
        for (Article article : articles) {
            int timesSold = new TimesSoldDecorator(article,receiptRepository).getTimesSold();
            result.add(new StatisticEntry(article,timesSold));
        }
        return result;
    }

    /**
     * Calculates how many times each article has been sold since a specified date
     *
     * @param date
     * @return Times sold since date for each article
     * @throws ServiceException
     */
    public List<StatisticEntry> timesSoldSince(Date date) throws ServiceException {
        logger.debug("Calculating times sold since "+date);
        List<StatisticEntry> result = new ArrayList<>();
        for (Article article : articles) {
            int timesSold = new TimesSoldDecorator(article, receiptRepository).getTimesSoldSince(date);
            result.add(new StatisticEntry(article,timesSold));
        }

        return result;
    }




}
