package ui.model;

import entities.Article;
import entities.Receipt;
import entities.ReceiptEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class ModelFactory {

    public ObservableList<ArticleModel> createArticleModels(List<Article> articles) {
        ObservableList<ArticleModel> result = FXCollections.observableArrayList();
        result.addAll(articles.stream().map(this::createArticleModel).collect(Collectors.toList()));
        return result;
    }

    public ArticleModel createArticleModel(Article article) {
        return new ArticleModel(article.getId(), article.getName(), article.getPrice(),
                article.getDescription(), article.getCategory(), article.getImage());
    }

    public ReceiptEntryModel createReceiptEntryModel(ReceiptEntry entry) {
        Article article = entry.getArticle();
        return new ReceiptEntryModel(article.getName(), article.getCategory(), article.getPrice(), entry.getAmount());
    }

    public List<ReceiptModel> createReceiptModels(List<Receipt> receipts) {
        ObservableList<ReceiptModel> result = FXCollections.observableArrayList();
        result.addAll(receipts.stream().map(this::createReceiptModel).collect(Collectors.toList()));
        return result;
    }

    public ReceiptModel createReceiptModel(Receipt receipt) {
        double totalCost = calculateTotalCost(receipt);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return new ReceiptModel(dateFormat.format(receipt.getDate()), receipt.getReceiver(), receipt.getReceiverAddress(), totalCost, receipt.getReceiptEntries());
    }

    public List<ReceiptEntryModel> createReceiptEntryModels(List<ReceiptEntry> receiptEntries) {
        ObservableList<ReceiptEntryModel> result = FXCollections.observableArrayList();
        result.addAll(receiptEntries.stream().map(this::createReceiptEntryModel).collect(Collectors.toList()));
        return result;
    }

    private double calculateTotalCost(Receipt receipt) {
        double sum = 0;
        for (ReceiptEntry entry : receipt.getReceiptEntries()) {
            sum += entry.getAmount() * entry.getArticle().getPrice();
        }
        return sum;
    }
}
