package service.calculation;

import entities.Article;
import service.decorator.ArticleSale;
import service.decorator.ArticleSaleFactory;

import java.util.ArrayList;
import java.util.List;

public class ArticleStatistic {

    private ArticleSaleFactory factory;
    private List<? extends Article> articleDtos;

    public ArticleStatistic(ArticleSaleFactory factory, List<? extends Article> articles) {
        this.factory = factory;
        this.articleDtos = articles;
    }

    public List<ArticleSale> createStatistic() {
        List<ArticleSale> result = new ArrayList<>();
        for (Article article : articleDtos) {
            ArticleSale sale = factory.create(article);
            result.add(sale);
        }
        return result;
    }

}
