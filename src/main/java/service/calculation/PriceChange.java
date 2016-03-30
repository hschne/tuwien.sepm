package service.calculation;

import entities.Article;
import service.ArticleRepository;
import service.ServiceException;

import java.util.List;

public class PriceChange {

    private ArticleRepository repository;
    private List<? extends Article> articleDtos;

    public PriceChange(ArticleRepository repository, List<? extends Article> articleDtos) {
        this.repository = repository;
        this.articleDtos = articleDtos;
    }

    public void changeByPercent(double percent) throws ServiceException {
        validatePercentage(percent);
        for (Article article : articleDtos) {

            double price = article.getPrice();
            article.setPrice(price + price * (percent / 100));
            repository.update(article);
        }
    }


    public void changeByAbsolute(double value) throws ServiceException {
        for (Article article : articleDtos) {
            validateAbsolute(value, article);
            double price = article.getPrice();
            article.setPrice(price + value);
            repository.update(article);
        }
    }


    private void validateAbsolute(double value, Article article) throws ServiceException {
        if (value < 0 && Math.abs(value) > article.getPrice())
            throw new ServiceException("Can not decrease by value greater than original price");
    }


    private void validatePercentage(double percent) throws ServiceException {
        if (percent <= -95) {
            throw new ServiceException("Discount cannot be more than 95 %");
        }
    }


}
