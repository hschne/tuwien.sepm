package ui.model;

import entities.Article;
import entities.Dto;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * UI implementation of Article DTO.
 * @see entities.Article
 */
public class ArticleModel extends Dto implements Article {

    private final StringProperty name;

    private final StringProperty category;

    private final DoubleProperty price;

    private final StringProperty description;

    private String image;

    private int Id;


    public ArticleModel(int id, String name, Double price, String description, String category, String image) {
        this(name, price, description, category, image);
        this.Id = id;
    }

    public ArticleModel(String name, Double price, String description, String category, String image) {
        this.image = image;
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.category = new SimpleStringProperty(category);
        this.description = new SimpleStringProperty(description);
    }

    public ArticleModel() {
        this.name = new SimpleStringProperty("");
        this.price = new SimpleDoubleProperty(0.0);
        this.category = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.image = "dummyImage.png";
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String getCategory() {
        return category.get();
    }

    @Override
    public void setCategory(String category) {
        this.category.set(category);
    }

    @Override
    public double getPrice() {
        return price.get();
    }

    @Override
    public void setPrice(double price) {
        this.price.set(price);
    }

    @Override
    public String getDescription() {
        return description.get();
    }

    @Override
    public void setDescription(String description) {
        this.description.set(description);
    }

    @Override
    public String getName() {
        return name.get();
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
    }

    @Override
    public int getId() {
        return Id;
    }

    @Override
    public void setId(int id) {
        Id = id;
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public StringProperty getDescriptionProperty() {
        return description;
    }

    public DoubleProperty getPriceProperty() {
        return price;
    }

    public StringProperty getCategoryProperty() {
        return category;
    }


}
