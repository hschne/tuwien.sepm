package service;

class ArticleCriteria {

    private String name;

    private String category;

    private DoubleCriteria priceCriteria;

    public ArticleCriteria(String name, String category, DoubleCriteria priceCriteria) {
        this.name = name;
        this.category = category;
        this.priceCriteria = priceCriteria;
    }

    public DoubleCriteria getPriceCritera(){
        return priceCriteria;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}
