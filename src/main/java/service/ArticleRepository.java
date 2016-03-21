package service;

import dao.ArticleDao;
import dao.DaoException;
import entities.Article;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArticleRepository extends AbstractService {

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
            } catch (DaoException e) {
                logger.error("Article retrieval from database failed.",e);
                throw new ServiceException("Could not retrieve articles", e);
            }
        }
        return articles;
    }


}
