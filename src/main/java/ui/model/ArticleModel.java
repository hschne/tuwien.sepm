package ui.model;

import entities.Article;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ArticleModel implements Article {

    private final StringProperty name;

    private final StringProperty category;

    private final DoubleProperty price;

    private final StringProperty description;

    private final Image image;


    public ArticleModel(String name, Double price, String description, String category,String image) {
        this.image = null;
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.category = new SimpleStringProperty(category);
        this.description = new SimpleStringProperty(description);
    }

    @Override
    public String getImage() {
        return null;
    }

    @Override
    public void setImage(String image) {

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
        return 0;
    }

    @Override
    public void setId(int id) {
    }

    public StringProperty getNameProperty(){
        return name;
    }

    public StringProperty getDescriptionProperty(){
        return description;
    }

    public DoubleProperty getPriceProperty(){
        return price;
    }

    public StringProperty getCategoryProperty(){
        return category;
    }

    public Image getImageProperty(){
        return image;
    }
}
