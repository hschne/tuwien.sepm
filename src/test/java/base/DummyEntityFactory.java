package base;

import entities.Article;
import entities.Receipt;
import entities.ReceiptEntry;

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
            Article article = new Article(i, "name" + i, i, "description", "", "category" + i);
            result.add(article);
        }
        return result;
    }

    public static Receipt createDummyReceipt(List<Article> articles) throws ParseException {
       return createDummyReceipt("01/01/2001",articles);
    }


    public static Receipt createDummyReceipt(String date, List<Article> articles) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Receipt receipt =  new Receipt(dateFormat.parse(date), "Receiver1", "ReceiverAdress1", null);
        receipt.setReceiptEntries(createDummyReceiptEntries(articles, receipt));
        return receipt;
    }

    public static List<Receipt> createDummyReceipts(int count) throws ParseException {
        List<Receipt> receipts = new ArrayList<>();
        for(int i = 0; i< count; i++){
            List<Article> articles = createDummyArticles(i);
            receipts.add(createDummyReceipt(articles));
        }
        return receipts;
    }

    static List<ReceiptEntry> createDummyReceiptEntries(List<Article> articles, Receipt receipt) {
        List<ReceiptEntry> entries = articles.stream().map(article -> new ReceiptEntry(receipt, article, 1)).collect(Collectors.toList());
        return entries;
    }
}
