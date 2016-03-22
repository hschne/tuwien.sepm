package service;

import dao.ArticleDao;
import dao.DaoException;
import entities.Article;
import entities.ArticleDto;
import service.criteria.Criteria;

import java.util.List;

public class ArticleRepository extends AbstractService implements Repository<Article> {

    private ArticleDao dao;
    private List<Article> articleDtos;

    public ArticleRepository(ArticleDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Article> getAll() throws ServiceException {
        logger.debug("Retrieving all articleDtos");
        if(articleDtos == null){
            try {
                articleDtos = dao.getVisible();
            } catch (DaoException e) {
                logger.error("ArticleDto retrieval from database failed.",e);
                throw new ServiceException("Could not retrieve articleDtos", e);
            }
        }
        return articleDtos;
    }

    @Override
    public List<Article> filter(Criteria<Article> criteria) throws ServiceException {
        return criteria.apply(getAll());
    }

}
