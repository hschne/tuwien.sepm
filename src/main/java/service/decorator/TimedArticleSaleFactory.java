package service.decorator;

import entities.Article;
import entities.ArticleDto;
import service.ReceiptRepository;
import service.criteria.DateOperator;

import java.util.Date;

public class TimedArticleSaleFactory extends ArticleSaleFactory {

    private final Date date;
    private final DateOperator operator;

    public TimedArticleSaleFactory(ReceiptRepository repository, Date date, DateOperator operator) {
        super(repository);
        this.date = date;
        this.operator = operator;
    }

    @Override
    public ArticleDtoSale create(Article articleDto) {
        return new TimedArticleDtoSale(repository, articleDto, date, operator);
    }
}
