package service.calculation;

import dao.ArticleDao;
import dao.DaoException;
import entities.ArticleDto;
import service.ServiceException;

import java.util.List;

public class PriceChange {

    private List<ArticleDto> articleDtos;

    private ArticleDao dao;

    public PriceChange(ArticleDao dao, List<ArticleDto> articleDtos) {
        this.articleDtos = articleDtos;
        this.dao = dao;
    }

    public void raiseByPercent(double percent) throws ServiceException {
        validatePercentage(percent);
        for (ArticleDto articleDto : articleDtos) {
            double price = articleDto.getPrice();
            articleDto.setPrice(price + price * (percent / 100));
            tryUpdateArticle(articleDto);
        }
    }

    public void reduceByPercent(double percent) throws ServiceException {
        validatePercentage(percent);
        validateDiscount(percent);
        for (ArticleDto articleDto : articleDtos) {
            double price = articleDto.getPrice();
            double newPrice = price - price * (percent / 100);
            articleDto.setPrice(newPrice);
            tryUpdateArticle(articleDto);
        }
    }

    public void raiseByAbsolute(double value) throws ServiceException {
        for (ArticleDto articleDto : articleDtos) {
            double price = articleDto.getPrice();
            articleDto.setPrice(price + value);
            tryUpdateArticle(articleDto);
        }
    }

    public void reduceByAbsolute(double value) throws ServiceException {
        validateAbsolute(value);
        for (ArticleDto articleDto : articleDtos) {
            double price = articleDto.getPrice();
            articleDto.setPrice(price - value);
            tryUpdateArticle(articleDto);
        }
    }

    private void validateAbsolute(double value) throws ServiceException {
        for (ArticleDto articleDto : articleDtos) {
            if (value > articleDto.getPrice())
                throw new ServiceException("Can not decrease by value greater than original price");
        }
    }

    private void tryUpdateArticle(ArticleDto articleDto) throws ServiceException {
        try {
            dao.update(articleDto);
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
