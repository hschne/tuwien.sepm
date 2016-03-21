package service.decorator;

import entities.Article;
import service.ReceiptRepository;
import service.ServiceException;

import java.util.Date;
import java.util.stream.Collectors;

public class TimesSoldDecorator {

    private final Article article;
    private final ReceiptRepository receiptRepository;

    public TimesSoldDecorator(Article article, ReceiptRepository repository) {
        this.article = article;
        this.receiptRepository = repository;
    }

    public int getTimesSoldSince(Date date) throws ServiceException {
        SoldDateDecorator decorator = new SoldDateDecorator(article, receiptRepository);
        return decorator.getSoldDates().stream()
                .filter(p -> p.after(date)).collect(Collectors.toList()).size();

    }

    public int getTimesSold() throws ServiceException {
        SoldDateDecorator decorator = new SoldDateDecorator(article, receiptRepository);
        return decorator.getSoldDates().size();

    }

}
