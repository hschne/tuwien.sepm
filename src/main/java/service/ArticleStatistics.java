package service;

import entities.Article;

import java.util.Date;
import java.util.List;

public class ArticleStatistics {

    private ArticleRepository articleRepository;

    private ReceiptRepository receiptRepository;

    public ArticleStatistics(ArticleRepository articleRepository, ReceiptRepository receiptRepository){
        this.articleRepository = articleRepository;
        this.receiptRepository = receiptRepository;
    }

    public int amountSold(Article article){
        return 0;
    }

    public int amountSold(List<Article> article){
        return 0;
    }

    public int amountSoldSince(List<Article> article, Date date){
        return 0;
    }

    public int amountSoldSince(Article article){
        return 0;
    }

}
