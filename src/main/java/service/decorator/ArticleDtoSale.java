package service.decorator;

import entities.*;
import service.ReceiptRepository;
import service.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleDtoSale extends ArticleDto {

    private ReceiptRepository repository;
    private final Article articleDto;

    ArticleDtoSale(ReceiptRepository repository, Article articleDto){
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
        return receiptEntries.stream()
                .filter(p -> p.getArticle().equals(articleDto)).collect(Collectors.toList());
    }




}
