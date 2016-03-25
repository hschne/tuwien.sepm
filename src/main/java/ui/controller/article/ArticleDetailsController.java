package ui.controller.article;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import service.ServiceException;
import ui.model.ArticleModel;

public class ArticleDetailsController extends AbstractController {

    private ArticleModel article;

    private boolean isNew;

    @FXML
    public TextField name;
    @FXML
    public TextField price;
    @FXML
    public TextArea description;
    @FXML
    public TextField category;

    public void initializeWith(ArticleModel article) {
        if (article == null) {
            this.article = new ArticleModel("", 0.0, "", "", "");
            isNew = true;
        } else {
            this.article = article;
            this.name.disableProperty().set(true);
            isNew = false;
        }
        name.setText(this.article.getName());
        price.setText(String.valueOf(this.article.getPrice()));
        description.setText(this.article.getDescription());
        category.setText(this.article.getCategory());
    }

    @FXML
    public void handleCancel() {
        mainApp.showArticleOverview();
    }

    @FXML
    public void handleSave() {
        try {
            article.setName(name.getText());
            article.setPrice(Double.parseDouble(price.getText()));
            article.setDescription(description.getText());
            article.setCategory(category.getText());
            if (!isNew) {
                mainApp.getArticleList().update(article);
            }
            else{
                mainApp.getArticleList().get().add(article);
            }
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
