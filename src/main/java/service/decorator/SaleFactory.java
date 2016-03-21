package service.decorator;

import entities.Article;

public interface SaleFactory {

    public ArticleSale create(Article article);
}
