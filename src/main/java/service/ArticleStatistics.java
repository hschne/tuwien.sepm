package service;

import entities.Article;
import entities.Receipt;
import entities.ReceiptEntry;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleStatistics {

    private ReceiptRepository receiptRepository;

    private List<Article> articles;

    public ArticleStatistics(ReceiptRepository receiptRepository, List<Article> articles) {
        this.receiptRepository = receiptRepository;
        this.articles = articles;
    }

    public List<StatisticEntry> timesSold() throws CalculationException {
        List<StatisticEntry> result = new ArrayList<>();
        for (Article article : articles) {
            result.add(timesSold(article));
        }

        return result;
    }

    private StatisticEntry timesSold(Article article) throws CalculationException {
        try {
            List<ReceiptEntry> receiptEntries = getReceiptEntries();
            List<ReceiptEntry> entriesWithArticle = receiptEntries.stream()
                    .filter(p -> p.getArticle().getId() == article.getId()).collect(Collectors.toList());
            int count = entriesWithArticle.stream().mapToInt(ReceiptEntry::getAmount).sum();
            return new StatisticEntry(article, count);
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

}
