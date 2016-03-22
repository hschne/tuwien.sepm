package service.calculation;

import entities.ArticleDto;
import service.decorator.ArticleDtoSale;
import service.decorator.ArticleSaleFactory;

import java.util.ArrayList;
import java.util.List;

public class ArticleStatistic {

    private ArticleSaleFactory factory;
    private List<ArticleDto> articleDtos;

    public ArticleStatistic(ArticleSaleFactory factory, List<ArticleDto> articleDtos) {
        this.factory = factory;
        this.articleDtos = articleDtos;
    }

    public List<ArticleDtoSale> createStatistic() {
        List<ArticleDtoSale> result = new ArrayList<>();
        for (ArticleDto articleDto : articleDtos) {
            ArticleDtoSale sale = factory.create(articleDto);
            result.add(sale);
        }
        return result;
    }

}
