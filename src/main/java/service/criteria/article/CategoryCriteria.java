package service.criteria.article;

import entities.Article;
import service.criteria.Criteria;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryCriteria implements Criteria<Article> {

    private String name;

    public CategoryCriteria(String name) {
        this.name = name;
    }

    @Override
    public List<Article> apply(List<Article> list) {
        return list.stream().filter(p -> p.getCategory().equals(name)).collect(Collectors.toList());
    }
}