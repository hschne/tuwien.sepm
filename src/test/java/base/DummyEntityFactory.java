package base;

import entities.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DummyEntityFactory {

    //Returns articles with price from 0..4 and names name1...name4 etc.
    public static List<Article> createDummyArticles(int count) {
        List<Article> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ArticleDto articleDto = new ArticleDto(i, "name" + i, i, "description", "", "category" + i);
            result.add(articleDto);
        }
        return result;
    }

    public static Receipt createDummyReceipt(List<Article> articleDtos) throws ParseException {
       return createDummyReceipt("01/01/2001", articleDtos);
    }


    public static Receipt createDummyReceipt(String date, List<Article> articleDtos) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Receipt receiptDto =  new ReceiptDto(dateFormat.parse(date), "Receiver1", "ReceiverAdress1", null);
        receiptDto.setReceiptEntries(createDummyReceiptEntries(articleDtos, receiptDto));
        return receiptDto;
    }

    public static List<Receipt> createDummyReceipts(int count) throws ParseException {
        List<Receipt> receiptDtos = new ArrayList<>();
        for(int i = 0; i< count; i++){
            List<Article> articleDtos = createDummyArticles(i);
            receiptDtos.add(createDummyReceipt(articleDtos));
        }
        return receiptDtos;
    }

    static List<ReceiptEntry> createDummyReceiptEntries(List<Article> articleDtos, Receipt receiptDto) {
        List<ReceiptEntry> entries = articleDtos.stream().map(article -> new ReceiptEntryDto(receiptDto, article, 1)).collect(Collectors.toList());
        return entries;
    }
}
