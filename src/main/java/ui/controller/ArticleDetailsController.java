package ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ui.model.ArticleModel;

public class ArticleDetailsController extends AbstractController {

    private ArticleModel article;

    @FXML
    private Label name;
    @FXML
    private TextField price;
    @FXML
    private TextArea description;
    @FXML
    private TextField category;

    public void setArticle(ArticleModel article) {
        this.article = article;
        name.setText(article.getName());
        price.setText(String.valueOf(article.getPrice()));
        description.setText(article.getDescription());
        category.setText(article.getCategory());
    }

    @FXML
    private void initialize() {

    }
}
