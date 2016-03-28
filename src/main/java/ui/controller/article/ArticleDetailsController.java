package ui.controller.article;

import dao.DaoException;
import dao.h2.ImageFile;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
            changeImage();
            if (!isNew) {
                mainApp.getArticleList().update(article);
            } else {
                mainApp.getArticleList().get().add(article);
            }
            mainApp.showArticleOverview();
        } catch (ServiceException e) {
            Output output = mainApp.getOutput();
            output.showNotification(Alert.AlertType.ERROR, "Error", "Could not update article.", e.getMessage());
        }
    }

    private void changeImage() {
        article.setImage(imagePath);
    }

    @FXML
    public void handleImageClick() {
        File file = mainApp.openFile();
        if(file != null){
            imagePath = file.getPath();
            image.setImage(new Image(file.toURI().toString()));
        }
    }


    @FXML
    private void initialize() {

    }

    private void checkForChanges() {

    }

    private void validateInput() {

    }
}
