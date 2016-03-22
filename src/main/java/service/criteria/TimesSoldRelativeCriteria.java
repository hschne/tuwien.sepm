package service.criteria;

import entities.Article;
import entities.ArticleDto;
import service.ServiceException;
import service.decorator.ArticleDtoSale;
import service.decorator.SaleFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TimesSoldRelativeCriteria implements Criteria<Article> {

    private final int count;
    private final RelativeOperator relativeOperator;
    private SaleFactory factory;

    public TimesSoldRelativeCriteria(SaleFactory factory, int count, RelativeOperator relativeOperator) {
        this.factory = factory;
        this.count = count;
        this.relativeOperator = relativeOperator;
    }

    @Override
    public List<Article> apply(List<Article> list) throws ServiceException {
        Collections.sort(list, createCustomComparator());
        return list.subList(0, count);
    }

    private Comparator<Article> createCustomComparator() {
        return (o1, o2) -> {
            ArticleDtoSale leftSale = factory.create(o1);
            ArticleDtoSale rightSale = factory.create(o2);
            try {
                if (relativeOperator == RelativeOperator.TOP) {
                    return getOrder(leftSale, rightSale);
                } else {
                    return getOrder(rightSale, leftSale);
                }
            } catch (ServiceException e) {
                return 0;
            }
        };
    }

    private int getOrder(ArticleDtoSale o1, ArticleDtoSale o2) throws ServiceException {
        if (o1.getTimesSold() > o2.getTimesSold()) {
            return -1;
        } else if (o1.getTimesSold() < o2.getTimesSold()) {
            return 1;
        } else {
            return 0;
        }
    }


}
