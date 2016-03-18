package service;

import entities.Article;
import entities.Receipt;
import entities.ReceiptEntry;
import service.filter.DatePredicate;
import service.filter.Operator;
import service.filter.ReceiptCriteria;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleStatistics implements Statistic {

    private ReceiptRepository receiptRepository;

    private List<Article> articles;

    public ArticleStatistics(ReceiptRepository receiptRepository, List<Article> articles) {
        this.receiptRepository = receiptRepository;
        this.articles = articles;
    }

    /**
     * Calculates how many times each article has been sold
     * @return Times sold for each article
     * @throws CalculationException
     */
    public List<StatisticEntry> timesSold() throws CalculationException {
        List<StatisticEntry> result = new ArrayList<>();
        for (Article article : articles) {
            result.add(timesSold(article));
        }
        return result;
    }

    /**
     * Calculates how many times each article has been sold since a specified date
     * @param date
     * @return Times sold since date for each article
     * @throws CalculationException
     */
    public List<StatisticEntry> timesSoldSince(Date date) throws CalculationException {
        List<StatisticEntry> result = new ArrayList<>();
        for (Article article : articles) {
            result.add(timesSoldSince(article, date));
        }

        return result;
    }

    private StatisticEntry timesSoldSince(Article article, Date date) throws CalculationException {
        try {
            List<ReceiptEntry> receiptEntries = getReceiptEntriesSince(date);
            return countArticlesSold(article, receiptEntries);
        } catch (SQLException e) {
            throw new CalculationException(e);
        }
    }

    private StatisticEntry timesSold(Article article) throws CalculationException {
        try {
            List<ReceiptEntry> receiptEntries = getReceiptEntries();
            return countArticlesSold(article, receiptEntries);
        } catch (SQLException e) {
            throw new CalculationException(e);
        }
    }

    private List<ReceiptEntry> getReceiptEntries() throws SQLException {
        List<Receipt> receipts = receiptRepository.getReceipts();
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        for (Receipt receipt : receipts) {
            receiptEntries.addAll(receipt.getReceiptEntries());
        }
        return receiptEntries;
    }

    private List<ReceiptEntry> getReceiptEntriesSince(Date date) throws SQLException {
        DatePredicate criteria = new DatePredicate(date, Operator.GREATER);
        List<Receipt> receipts = receiptRepository.filter(new ReceiptCriteria(null, criteria, null));
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        for (Receipt receipt : receipts) {
            receiptEntries.addAll(receipt.getReceiptEntries());
        }
        return receiptEntries;
    }


    private StatisticEntry countArticlesSold(Article article, List<ReceiptEntry> receiptEntries) {
        List<ReceiptEntry> entriesWithArticle = receiptEntries.stream()
                .filter(p -> p.getArticle().getId() == article.getId()).collect(Collectors.toList());
        int count = entriesWithArticle.stream().mapToInt(ReceiptEntry::getAmount).sum();
        return new StatisticEntry(article, count);
    }


}
