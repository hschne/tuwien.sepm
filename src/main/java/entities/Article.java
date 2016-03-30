package entities;

/**
 * Implementors are article data transfer objects
 */
public interface Article extends Entity{

    String getImage();

    void setImage(String image);

    String getCategory();

    void setCategory(String category);

    double getPrice();

    void setPrice(double price);

    String getDescription();

    void setDescription(String description);

    String getName();

    void setName(String name);

}
