package service.filter;

import java.util.List;

public class ReceiptCriteria implements Criteria{

    private String receiver;

    private DatePredicate datePredicate;

    private NumberPredicate articleCount;

    public ReceiptCriteria(String receiver, DatePredicate datePredicate, NumberPredicate articleCount) {
        this.receiver = receiver;
        this.datePredicate = datePredicate;
        this.articleCount = articleCount;
    }

    public String getReceiver() {
        return receiver;
    }

    public DatePredicate getDatePredicate() {
        return datePredicate;
    }

    public NumberPredicate getArticleCountCriteria() {
        return articleCount;
    }

    @Override
    public List apply(List list) {
        return null;
    }
}
