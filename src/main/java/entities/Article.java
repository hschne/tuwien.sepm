package entities;


public class Article extends AbstractEntity {

    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int id;

    public Article() {

    }

    public Article(int id, String name, double price, String description, String image, String category) {
        this(name, description, image, category, price);
        this.id = id;

    }

    public Article(String name, String description, String image, String category, double price) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.category = category;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
