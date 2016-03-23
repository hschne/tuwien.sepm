package ui.model;

import entities.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class ModelFactory {

    public ObservableList<ArticleModel> createArticleModels(List<Article> articles) {
        ObservableList<ArticleModel> result = FXCollections.observableArrayList();
        result.addAll(articles.stream().map(this::createArticleModel).collect(Collectors.toList()));
        return result;
    }

    public ArticleModel createArticleModel(Article article) {
        return new ArticleModel(article.getId(),article.getName(), article.getPrice(),
                article.getDescription(), article.getCategory(), article.getImage());
    }
}
