package service.filter;

public class ArticleCriteria {

    private String name;

    private String category;

    private NumberCriteria priceCriteria;

    public ArticleCriteria(String name, String category, NumberCriteria priceCriteria) {
        this.name = name;
        this.category = category;
        this.priceCriteria = priceCriteria;
    }

    public NumberCriteria getPriceCritera(){
        return priceCriteria;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}
