package service.criteria;

import entities.Article;

import java.util.List;
import java.util.stream.Collectors;

public class NameCriteria implements Criteria<Article> {

    private String name;

    public NameCriteria(String name){
        this.name = name;
    }

    @Override
    public List<Article> apply(List<Article> list) {
        return list.stream().filter(p -> p.getName().equals(name)).collect(Collectors.toList());
    }
}
