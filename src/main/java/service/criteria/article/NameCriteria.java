package service.criteria.article;

import entities.Article;
import org.apache.commons.lang3.StringUtils;
import service.criteria.Criteria;

import java.util.List;
import java.util.stream.Collectors;

public class NameCriteria implements Criteria<Article> {

    private String name;

    public NameCriteria(String name) {
        this.name = name;
    }

    @Override
    public List<Article> apply(List<? extends Article> list) {
        return list.stream().filter(p -> StringUtils.containsIgnoreCase(p.getName(),name)).collect(Collectors.toList());
    }
}
