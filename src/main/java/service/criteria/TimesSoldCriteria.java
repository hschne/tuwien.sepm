package service.criteria;

import service.ReceiptRepository;
import service.ServiceException;
import service.decorator.ArticleSale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimesSoldCriteria implements Criteria<ArticleSale> {

    private final int times;
    private final Operator operator;

    
    public TimesSoldCriteria(int times, Operator operator) {
        this.times = times;
        this.operator = operator;

    }

    @Override
    public List<ArticleSale> apply(List<ArticleSale> list) throws ServiceException {
        List<ArticleSale> result = new ArrayList<>();
        for (ArticleSale article : list) {
            if (articleMatches(article)) {
                result.add(article);
            }
        }
        return result;
    }

    private boolean articleMatches(ArticleSale article) throws ServiceException {
        switch (operator) {
            case GREATER:
                return article.getTimesSold() > times;
            case GREATER_EQUALS:
                return article.getTimesSold() >= times;
            case LOWER:
                return article.getTimesSold() < times;
            case LOWER_EQUALS:
                return article.getTimesSold() <= times;
            case EQUALS:
                return article.getTimesSold() == times;
            default:
                throw new ServiceException("Unsupported Operator");
        }
    }


}
