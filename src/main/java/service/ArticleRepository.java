package service;

import dao.ArticleDao;
import dao.DaoException;
import entities.Article;
import service.criteria.Criteria;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArticleRepository extends AbstractService implements Repository<Article> {

    private ArticleDao dao;
    private List<Article> articles;

    public ArticleRepository(ArticleDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Article> getAll() throws ServiceException {
        logger.debug("Retrieving all articles");
        if(articles == null){
            try {
                articles = dao.getVisible();
            } catch (DaoException e) {
                logger.error("Article retrieval from database failed.",e);
                throw new ServiceException("Could not retrieve articles", e);
            }
        }
        return articles;
    }

    @Override
    public List<Article> filter(Criteria<Article> criteria) throws ServiceException {
        return criteria.apply(getAll());
    }

}
