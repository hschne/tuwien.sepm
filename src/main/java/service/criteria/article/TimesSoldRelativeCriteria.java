package service.criteria.article;

import entities.Article;
import service.ServiceException;
import service.criteria.Criteria;
import service.criteria.operator.RelativeOperator;
import service.decorator.ArticleSale;
import service.decorator.SaleFactory;

import java.util.ArrayList;
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
    public List<Article> apply(List<? extends Article> list) throws ServiceException {
        Collections.sort(list, createCustomComparator());
        List<Article> result = new ArrayList<>();
        for(int i = 0; i<count; i++){
            result.add(list.get(i));
        }
        return result;
    }

    private Comparator<Article> createCustomComparator() {
        return (o1, o2) -> {
            ArticleSale leftSale = factory.create(o1);
            ArticleSale rightSale = factory.create(o2);
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

    private int getOrder(ArticleSale o1, ArticleSale o2) throws ServiceException {
        if (o1.getTimesSold() > o2.getTimesSold()) {
            return -1;
        } else if (o1.getTimesSold() < o2.getTimesSold()) {
            return 1;
        } else {
            return 0;
        }
    }


}
