package service.filter;

import service.ReceiptRepository;
import service.decorator.TimesSoldDecorator;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class SoldDate<Article extends entities.Article> implements Criteria<Article> {

    private Date date;

    private DateOperator operator;

    private ReceiptRepository receiptRepository;

    public SoldDate(Date date, DateOperator operator, ReceiptRepository receiptRepository) {
        this.date = date;
        this.operator = operator;
        this.receiptRepository = receiptRepository;
    }


    @Override
    public List<Article> apply(List<Article> list) {
        for (Article article : list) {
            TimesSoldDecorator timesSoldDecorator = new TimesSoldDecorator(article, receiptRepository);

        }
        return null;
    }

    private Predicate<Article> createPredicate() {
        switch (operator) {
            case BEFORE:
                break;
            case AFTER:
                break;
            case AT:
                break;
        }
        return null;
    }
}
