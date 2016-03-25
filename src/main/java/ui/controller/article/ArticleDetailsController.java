package ui.controller.article;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import service.ServiceException;
import ui.Output;
import ui.model.ArticleModel;

import java.io.File;

public class ArticleDetailsController extends AbstractController {

    @FXML
    public TextField name;
    @FXML
    public TextField price;
    @FXML
    public TextArea description;
    @FXML
    public TextField category;
    @FXML
    public ImageView image;
    private ArticleModel article;
    private boolean isNew;

    public void initializeWith(ArticleModel article) {
        if (article == null) {
            this.article = new ArticleModel("", 0.0, "", "", "");
            isNew = true;
        } else {
            this.article = article;
            this.name.disableProperty().set(true);
            this.image.setImage(article.getActualImage());
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
            } else {
                mainApp.getArticleList().get().add(article);
            }
        } catch (ServiceException e) {
            Output output = mainApp.getOutput();
            output.showNotification(Alert.AlertType.ERROR, "Error", "Could not update article.", "");
        }
        mainApp.showArticleOverview();
    }

    @FXML
    public void handleImageClick() {
        File file = mainApp.openFile();
        article.setImage(file.getPath());
        image.setImage(article.getActualImage());
    }

    @FXML
    private void initialize() {

    }

    private void checkForChanges() {

    }

    private void validateInput() {

    }
}
