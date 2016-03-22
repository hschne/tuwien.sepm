package ui.controller;

import entities.Article;
import ui.Main;
import ui.model.ArticleModel;

public class ArticleDetailsController extends AbstractController {

    private ArticleModel article;

    public void setArticle(ArticleModel article) {
        this.article = article;
    }
}
