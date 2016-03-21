package service.decorator;

import entities.Article;
import entities.Receipt;
import entities.ReceiptEntry;
import service.ReceiptRepository;
import service.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleSale extends Article {

    private ReceiptRepository repository;
    private final Article article;

    ArticleSale(ReceiptRepository repository, Article article){
        this.repository = repository;
        this.article = article;
    }

    public int getTimesSold() throws ServiceException {
        return entriesForArticle().size();
    }

    protected List<ReceiptEntry> entriesForArticle() throws ServiceException {
        List<Receipt> receipts = repository.getAll();
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        for (Receipt receipt : receipts) {
            receiptEntries.addAll(receipt.getReceiptEntries());
        }
        return receiptEntries.stream()
                .filter(p -> p.getArticle().equals(article)).collect(Collectors.toList());
    }




}
