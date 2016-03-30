package service;

import dao.ArticleDao;
import dao.DaoException;
import entities.Article;
import entities.ArticleDto;
import service.criteria.Criteria;
import ui.model.ArticleModel;

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
                logger.error("Could not retrieve articles.",e);
                throw new ServiceException("Could not retrieve articles.", e);
            }
        }
        return articleDtos;
    }

    @Override
    public List<Article> filter(Criteria<Article> criteria) throws ServiceException {
        return criteria.apply(getAll());
    }

    public void update(Article article) throws ServiceException {
        logger.debug("Update article " + article.toString());
        try {
            dao.update(article);
        } catch (DaoException e) {
            logger.error("Could not update article "+article.toString(), e);
            throw new ServiceException("Could not update article.",e);
        }
    }

    public void create(Article article) throws ServiceException {
        logger.debug("Create article "+article);
        try {
            dao.create(article);
            getAll().add(article);
        } catch (DaoException e) {
            logger.error("Could not create article "+article.toString(), e);
            throw new ServiceException("Could not create article.",e);
        }
    }

    public void delete(Article article) throws ServiceException {
        logger.debug("Delete article "+article);
        try {
            dao.delete(article);
            getAll().remove(article);
        } catch (DaoException e) {
            logger.error("Could not delete article "+article.toString(), e);
            throw new ServiceException("Could not delete article.",e);
        }
    }
}
