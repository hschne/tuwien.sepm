package service.criteria;

import service.ReceiptRepository;
import service.ServiceException;
import service.decorator.ArticleSale;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TimesSoldRelativeCriteria implements Criteria<ArticleSale> {

    private final ReceiptRepository repository;
    private final int count;
    private final RelativeOperator relativeOperator;

    public TimesSoldRelativeCriteria(ReceiptRepository repository, int count, RelativeOperator relativeOperator) {
        this.repository = repository;
        this.count = count;
        this.relativeOperator = relativeOperator;
    }

    @Override
    public List<ArticleSale> apply(List<ArticleSale> list) throws ServiceException {
        Collections.sort(list, createCustomComparator());
        return list.subList(0, count);
    }

    private Comparator<ArticleSale> createCustomComparator() {
        return (o1, o2) -> {
            try {
                if (relativeOperator == RelativeOperator.TOP) {
                    return getOrder(o1, o2);
                } else {
                    return getOrder(o2, o1);
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
        } else return 0;
    }


}
