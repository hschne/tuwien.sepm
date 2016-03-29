package service.decorator;

import entities.*;
import service.ReceiptRepository;
import service.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleSale extends ArticleDto {

    private ReceiptRepository repository;
    private final Article articleDto;

    ArticleSale(ReceiptRepository repository, Article articleDto){
        this.repository = repository;
        this.articleDto = articleDto;
    }

    public int getTimesSold() throws ServiceException {
        return entriesForArticle().size();
    }

    protected List<ReceiptEntry> entriesForArticle() throws ServiceException {
        List<Receipt> receiptDtos = repository.getAll();
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        for (Receipt receiptDto : receiptDtos) {
            receiptEntries.addAll(receiptDto.getReceiptEntries());
        }
        return receiptEntries.stream().filter(entry -> entry.getArticle().getId() == articleDto.getId()).collect(Collectors.toList());
    }




}
