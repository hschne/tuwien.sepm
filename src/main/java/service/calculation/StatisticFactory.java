package service.calculation;

import entities.Article;
import service.ReceiptRepository;
import service.criteria.DateOperator;
import service.decorator.ArticleSale;
import service.decorator.TimedArticleSale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatisticFactory {

    private ReceiptRepository repository;
    private List<Article> articles;

    public StatisticFactory(ReceiptRepository repository, List<Article> articles){
        this.repository = repository;
        this.articles = articles;
    }

    public List<ArticleSale> CreateAbsoluteSaleStatistic(){
        List<ArticleSale> result = new ArrayList<>();
        for(Article article : articles){
            ArticleSale sale = new ArticleSale(repository, article);
            result.add(sale);
        }
        return result;
    }

    public List<ArticleSale> CreateSoldSinceStatistic(Date date){
        List<ArticleSale> result = new ArrayList<>();
        for(Article article : articles){
            ArticleSale sale = new TimedArticleSale(repository, article,date, DateOperator.AFTER);
            result.add(sale);
        }
        return result;
    }
}
