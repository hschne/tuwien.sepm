package service.filter;

public class ArticleCriteria implements Criteria {

    private String name;

    private String category;

    private NumberPredicate priceCriteria;

    public ArticleCriteria(String name, String category, NumberPredicate priceCriteria) {
        this.name = name;
        this.category = category;
        this.priceCriteria = priceCriteria;
    }

    public NumberPredicate getPriceCritera(){
        return priceCriteria;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}
