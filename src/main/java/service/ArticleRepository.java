package service;

import dao.ArticleDao;
import entities.Article;
import service.filter.ArticleCriteria;
import service.filter.Filter;
import service.filter.NumberPredicate;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArticleRepository extends AbstractService implements Filter<ArticleCriteria>, Repository<Article> {

    private ArticleDao dao;
    private List<Article> articles;

    public ArticleRepository(ArticleDao dao) {
        this.dao = dao;
    }

    public List<Article> getAll() throws ServiceException {
        logger.debug("Retrieving all articles");
        if(articles == null){
            try {
                articles = dao.getVisible();
            } catch (SQLException e) {
                logger.error("Article retrieval from database failed.",e);
                throw new ServiceException("Could not retrieve articles", e);
            }
        }
        return articles;
    }

    public List<Article> filter(ArticleCriteria criteria) throws ServiceException{
        List<Article> filteredArticles;
        filteredArticles = getAll();
        if (criteria.getCategory() != null){
            filteredArticles = filteredArticles.stream()
                    .filter(p -> Objects.equals(p.getCategory(), criteria.getCategory())).collect(Collectors.toList());
        }
        if(criteria.getName() != null){
            filteredArticles = filteredArticles.stream()
                    .filter(p -> Objects.equals(p.getName(), criteria.getName())).collect(Collectors.toList());
        }
        if(criteria.getPriceCritera() != null ){
            NumberPredicate priceCriteria = criteria.getPriceCritera();
            filteredArticles = filteredArticles.stream()
                    .filter(p -> priceCriteria.compare(p.getPrice())).collect(Collectors.toList());
        }
        return filteredArticles;
    }


}
