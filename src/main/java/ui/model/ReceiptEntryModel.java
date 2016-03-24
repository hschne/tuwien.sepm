package ui.model;

import entities.Article;
import entities.Receipt;
import entities.ReceiptEntry;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReceiptEntryModel implements ReceiptEntry {

    private final StringProperty name;

    private final StringProperty category;

    private final DoubleProperty price;

    public ReceiptEntryModel(String name, String category, Double price) {
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleStringProperty(category);
        this.price = new SimpleDoubleProperty(price);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    @Override
    public Article getArticle() {
        return null;
    }

    @Override
    public void setArticle(Article articleDto) {

    }

    @Override
    public int getAmount() {
        return 0;
    }

    @Override
    public void setAmount(int amount) {

    }

    @Override
    public Receipt getReceipt() {
        return null;
    }

    @Override
    public void setReceipt(Receipt receiptDto) {

    }
}
