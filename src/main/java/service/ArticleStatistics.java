package service;

import entities.Article;

import java.util.List;

public class ArticleStatistics {

    private ReceiptRepository receiptRepository;

    private List<Article> articles;

    public ArticleStatistics(ReceiptRepository receiptRepository, List<Article> articles) {
        this.receiptRepository = receiptRepository;
        this.articles = articles;
    }

    public List<StatisticEntry> timesSold() {
        return null;
    }

}
