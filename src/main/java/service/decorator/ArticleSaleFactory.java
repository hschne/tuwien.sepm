package service.decorator;

import entities.Article;
import entities.ArticleDto;
import service.ReceiptRepository;

public class ArticleSaleFactory implements SaleFactory{

    protected ReceiptRepository repository;

    public ArticleSaleFactory(ReceiptRepository repository){
        this.repository = repository;
    }

    @Override
    public ArticleDtoSale create(Article articleDto) {
        return new ArticleDtoSale(repository, articleDto);
    }
}
