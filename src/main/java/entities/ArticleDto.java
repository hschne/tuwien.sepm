package entities;

public class ArticleDto extends Dto implements Article {

    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int id;

    public ArticleDto() {
        //Only used for unit testing
    }

    public ArticleDto(int id, String name, double price, String description, String image, String category) {
        this(name, description, image, category, price);
        this.id = id;

    }

    public ArticleDto(String name, String description, String image, String category, double price) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.category = category;
        this.price = price;
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
        return category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }


}
