package ui.model;

import entities.Article;
import entities.Receipt;
import entities.ReceiptEntry;
import javafx.beans.property.*;

/**
 * UI Implementation of receipt entry DTO
 * @see entities.ReceiptEntry
 */
public class ReceiptEntryModel implements ReceiptEntry {

    private final StringProperty name;

    private final DoubleProperty price;

    private IntegerProperty amount;

    private Article article;

    private Receipt receipt;

    public ReceiptEntryModel(Article article, Receipt receipt, int amount){
        this(article, amount);
        this.receipt = receipt;
    }

    public ReceiptEntryModel(Article article, int amount){
        this(article.getName(),article.getPrice(), amount);
        this.article = article;
    }

    public ReceiptEntryModel(String name, Double price, int amount) {
        this.amount = new SimpleIntegerProperty(amount);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }


    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public IntegerProperty getAmountProperty(){ return amount;}

    @Override
    public Article getArticle() {
        return article;
    }

    @Override
    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public int getAmount() {
        return amount.getValue();
    }

    @Override
    public void setAmount(int amount) {
        this.amount.setValue(amount);
    }

    @Override
    public Receipt getReceipt() {
        return this.receipt;
    }

    @Override
    public void setReceipt(Receipt receiptDto) {
        this.receipt = receiptDto;
    }
}
