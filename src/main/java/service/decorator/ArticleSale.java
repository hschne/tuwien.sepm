package service.decorator;

import entities.Article;
import entities.ArticleDto;
import entities.Receipt;
import entities.ReceiptEntry;
import service.ReceiptRepository;
import service.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArticleSale extends ArticleDto {

    private final Article article;
    private ReceiptRepository repository;

    ArticleSale(ReceiptRepository repository, Article article) {
        this.repository = repository;
        this.article = article;
    }

    public int getTimesSold() throws ServiceException {
        return entriesForArticle().stream().mapToInt(ReceiptEntry::getAmount).sum();
    }

    protected List<ReceiptEntry> entriesForArticle() throws ServiceException {
        List<Receipt> receiptDtos = repository.getAll();
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        for (Receipt receiptDto : receiptDtos) {
            receiptEntries.addAll(receiptDto.getReceiptEntries());
        }
        return receiptEntries.stream().filter(entry -> Objects.equals(entry.getArticle().getName(), article.getName())).collect(Collectors.toList());
    }

}
