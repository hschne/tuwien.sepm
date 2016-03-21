package service.decorator;

import entities.Article;
import entities.Receipt;
import service.ReceiptRepository;
import service.ServiceException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SoldDateDecorator {

    private Article article;

    private ReceiptRepository receiptRepository;

    public SoldDateDecorator(Article article, ReceiptRepository receiptRepository) {
        this.article = article;
        this.receiptRepository = receiptRepository;
    }

    public List<Date> getSoldDates() throws ServiceException {
        List<Receipt> receipts = receiptRepository.getAll();
        List<Date> soldDates = new ArrayList<>();
        for (Receipt receipt : receipts) {
            Date date = receipt.getDate();
            if (receipt.getReceiptEntries().stream().anyMatch(p -> p.getArticle().getId() == article.getId())) {
                soldDates.add(date);
            }
        }
        return soldDates;
    }
}
