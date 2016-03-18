package service.calculation;

import dao.ArticleDao;
import dao.DaoException;
import entities.Article;
import service.ServiceException;

import java.util.List;

public class PriceChange {

    private List<Article> articles;

    private ArticleDao dao;

    public PriceChange(ArticleDao dao, List<Article> articles) {
        this.articles = articles;
        this.dao = dao;
    }

    public void raiseByPercent(double percent) throws ServiceException {
        validatePercentage(percent);
        for (Article article : articles) {
            double price = article.getPrice();
            article.setPrice(price + price * (percent / 100));
            tryUpdateArticle(article);
        }
    }

    public void reduceByPercent(double percent) throws ServiceException {
        validatePercentage(percent);
        validateDiscount(percent);
        for (Article article : articles) {
            double price = article.getPrice();
            double newPrice = price - price * (percent / 100);
            article.setPrice(newPrice);
            tryUpdateArticle(article);
        }
    }

    public void raiseByAbsolute(double value) throws ServiceException {
        for (Article article : articles) {
            double price = article.getPrice();
            article.setPrice(price + value);
            tryUpdateArticle(article);
        }
    }

    public void reduceByAbsolute(double value) throws ServiceException {
        validateAbsolute(value);
        for (Article article : articles) {
            double price = article.getPrice();
            article.setPrice(price - value);
            tryUpdateArticle(article);
        }
    }

    private void validateAbsolute(double value) throws ServiceException {
        for (Article article : articles) {
            if (value > article.getPrice())
                throw new ServiceException("Can not decrease by value greater than original price");
        }
    }

    private void tryUpdateArticle(Article article) throws ServiceException {
        try {
            dao.update(article);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private void validatePercentage(double percent) throws ServiceException {
        if (percent < 0) {
            throw new ServiceException("Percentage must be a positive number");
        }
    }

    private void validateDiscount(double percent) throws ServiceException {
        if (percent >= 95) {
            throw new ServiceException("Discount cannot be more than 95 %");
        }

    }


}
