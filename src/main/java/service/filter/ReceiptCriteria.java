package service.filter;

public class ReceiptCriteria {

    private String receiver;

    private DateCriteria dateCriteria;

    private NumberCriteria articleCount;


    public ReceiptCriteria(String receiver, DateCriteria dateCriteria, NumberCriteria articleCount) {
        this.receiver = receiver;
        this.dateCriteria = dateCriteria;
        this.articleCount = articleCount;
    }

    public String getReceiver() {
        return receiver;
    }

    public DateCriteria getDateCriteria() {
        return dateCriteria;
    }

    public NumberCriteria getArticleCountCriteria() {
        return articleCount;
    }

}
