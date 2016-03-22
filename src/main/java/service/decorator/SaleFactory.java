package service.decorator;

import entities.Article;
import entities.ArticleDto;

public interface SaleFactory {

    public ArticleDtoSale create(Article articleDto);
}
