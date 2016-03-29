package ui.controller.article;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import service.ServiceException;
import ui.Output;
import ui.controller.AbstractController;
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

    private boolean hasChanged;

    private String imagePath;

    public void initializeWith(ArticleModel article) {
        if (article == null) {
            this.article = new ArticleModel();
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
        image.setImage(this.article.getActualImage());
        imagePath = this.article.getImage();
        initializeChangeListeners();
    }

    @FXML
    public void handleCancel() {
        if (discardChanges()) {
            mainApp.showArticleOverview();
        }
    }

    @FXML
    public void handleSave() {
        try {
            article.setName(name.getText());
            article.setPrice(Double.parseDouble(price.getText()));
            article.setDescription(description.getText());
            article.setCategory(category.getText());
            changeImage();
            if (isNew) {
                if (hasChanged) {
                    mainApp.getArticleList().get().add(article);
                }
            } else {
                mainApp.getArticleList().update(article);
            }
            mainApp.showArticleOverview();
        } catch (ServiceException e) {
            Output output = mainApp.getOutput();
            output.showNotification(Alert.AlertType.ERROR, "Error", "Could not update article.", e.getMessage());
        }
    }

    @FXML
    public void handleImageClick() {
        File file = mainApp.openFile();
        if (file != null) {
            imagePath = file.getPath();
            image.setImage(new Image(file.toURI().toString()));
        }
    }

    private void changeImage() {
        article.setImage(imagePath);
    }

    private boolean discardChanges() {
        if (hasChanged) {
            Output output = new Output();
            return output.showConfirmationDialog("Unsaved Changes", "You have made changes", "Do you want to discard those changes?.");
        }
        return true;
    }

    private void initializeChangeListeners() {
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            hasChanged = true;
        });
        category.textProperty().addListener((observable, oldValue, newValue) -> {
            hasChanged = true;
        });
        description.textProperty().addListener((observable, oldValue, newValue) -> {
            hasChanged = true;
        });
        price.textProperty().addListener((observable, oldValue, newValue) -> {
            hasChanged = true;
        });
    }
}
