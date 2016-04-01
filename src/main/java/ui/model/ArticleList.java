package ui.model;

import entities.Article;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ArticleRepository;
import service.ServiceException;
import service.criteria.Criteria;

import java.util.List;


/**
 * Wrapper around the observable list containing all articles. Responsible for keeping in sync with service repository.
 */
public class ArticleList {

    private final Logger logger = LogManager.getLogger(ArticleList.class);
    private ArticleRepository articleRepository;
    private final ListChangeListener<ArticleModel> removeListener = c -> {
        while (c.next()) {
            removeItems(c);
        }
    };
    private final ListChangeListener<ArticleModel> addListener = c -> {
        while (c.next()) {
            addItems(c);
        }
    };
    private ObservableList<ArticleModel> articles;


    public ArticleList(ArticleRepository repository) throws ServiceException {
        this.articleRepository = repository;
        initializeList();
    }

    public ObservableList<ArticleModel> get() {
        return articles;
    }

    public void update(ArticleModel article) throws ServiceException {
        articleRepository.update(article);
    }

    public void applyFilter(Criteria<Article> criteria) throws ServiceException {
        clearList();
        addItems(criteria);
    }

    private void addItems(Criteria<Article> criteria) throws ServiceException {
        articles.removeListener(addListener);
        ModelFactory factory = new ModelFactory();
        articles.addAll(factory.createArticleModels(articleRepository.filter(criteria)));
        articles.addListener(addListener);
    }

    private void clearList() {
        articles.removeListener(removeListener);
        articles.clear();
        articles.addListener(removeListener);
    }

    private void initializeList() throws ServiceException {
        articles = FXCollections.observableArrayList();
        ModelFactory factory = new ModelFactory();
        articles.addAll(factory.createArticleModels(articleRepository.getAll()));
        articles.addListener(addListener);
        articles.addListener(removeListener);
    }

    private void addItems(ListChangeListener.Change<? extends ArticleModel> c) {
        for (ArticleModel additem : c.getAddedSubList()) {
            try {
                articleRepository.create(additem);
            } catch (ServiceException e1) {
                logger.error("Failed to create article");
            }
        }
    }

    private void removeItems(ListChangeListener.Change<? extends ArticleModel> c) {
        for (ArticleModel remitem : c.getRemoved()) {
            try {
                articleRepository.delete(remitem);
            } catch (ServiceException e) {
                logger.error("Failed to delete article");
            }
        }
    }
}
