package service.decorator;

import entities.Article;
import service.ReceiptRepository;

public class ArticleSaleFactory implements SaleFactory{

    protected ReceiptRepository repository;

    public ArticleSaleFactory(ReceiptRepository repository){
        this.repository = repository;
    }

    @Override
    public ArticleSale create(Article article) {
        return new ArticleSale(repository, article);
    }
}
