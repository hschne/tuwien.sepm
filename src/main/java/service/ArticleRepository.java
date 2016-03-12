package service;

import dao.ArticleDao;
import entities.Article;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArticleRepository {

    private ArticleDao dao;
    private List<Article> articles;

    public ArticleRepository(ArticleDao dao) {
        this.dao = dao;
    }

    public List<Article> getArticles() throws SQLException {
        if(articles == null){
            articles = dao.getVisible();
        }
        return articles;
    }

    public List<Article> filter(ArticleCriteria criteria) throws SQLException {
        List<Article> filteredArticles = getArticles();
        if (criteria.getCategory() != null){
            filteredArticles = getArticles().stream()
                    .filter(p -> Objects.equals(p.getCategory(), criteria.getCategory())).collect(Collectors.toList());
        }
        if(criteria.getName() != null){
            filteredArticles = getArticles().stream()
                    .filter(p -> Objects.equals(p.getName(), criteria.getName())).collect(Collectors.toList());
        }
        if(criteria.getPriceCritera() != null ){
            DoubleCriteria priceCriteria = criteria.getPriceCritera();
            filteredArticles = getArticles().stream()
                    .filter(p -> priceCriteria.compare(p.getPrice())).collect(Collectors.toList());
        }
        return filteredArticles;
    }


}
