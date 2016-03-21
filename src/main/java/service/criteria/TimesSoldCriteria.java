package service.criteria;

import entities.Article;
import service.ReceiptRepository;
import service.ServiceException;
import service.decorator.ArticleSale;
import service.decorator.SaleFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimesSoldCriteria implements Criteria<Article> {

    private SaleFactory factory;
    private final int times;
    private final Operator operator;


    public TimesSoldCriteria(SaleFactory factory, int times, Operator operator) {
        this.factory = factory;
        this.times = times;
        this.operator = operator;

    }

    @Override
    public List<Article> apply(List<Article> list) throws ServiceException {
        List<Article> result = new ArrayList<>();
        for (Article article : list) {
            if (articleMatches(article)) {
                result.add(article);
            }
        }
        return result;
    }

    private boolean articleMatches(Article article) throws ServiceException {
        ArticleSale articleSale = factory.create(article);
        switch (operator) {
            case GREATER:
                return articleSale.getTimesSold() > times;
            case GREATER_EQUALS:
                return articleSale.getTimesSold() >= times;
            case LOWER:
                return articleSale.getTimesSold() < times;
            case LOWER_EQUALS:
                return articleSale.getTimesSold() <= times;
            case EQUALS:
                return articleSale.getTimesSold() == times;
            default:
                throw new ServiceException("Unsupported Operator");
        }
    }


}
