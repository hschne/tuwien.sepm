package ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import service.ServiceException;
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
    public void handleCancel() {
        mainApp.showArticleOverview();
    }

    @FXML
    public void handleSave() {
        try {
            article.setPrice(Double.parseDouble(price.getText()));
            article.setDescription(description.getText());
            article.setCategory(category.getText());
            mainApp.getArticleRepository().update(article);
        } catch (ServiceException e) {
            mainApp.showNotification(Alert.AlertType.ERROR, "Error", "Could not update article.", "");
        }
        mainApp.showArticleOverview();
    }

    @FXML
    private void initialize() {

    }

    private void checkForChanges() {

    }

    private void validateInput() {

    }
}
