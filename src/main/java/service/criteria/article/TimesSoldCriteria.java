package service.criteria.article;

import entities.Article;
import service.ServiceException;
import service.criteria.Criteria;
import service.criteria.Operator;
import service.decorator.ArticleSale;
import service.decorator.SaleFactory;

import java.util.ArrayList;
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
        for (Article articleDto : list) {
            if (articleMatches(articleDto)) {
                result.add(articleDto);
            }
        }
        return result;
    }

    private boolean articleMatches(Article articleDto) throws ServiceException {
        ArticleSale articleSale = factory.create(articleDto);
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
