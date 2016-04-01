package ui.controller.article;

import dao.h2.ImageFile;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.lang3.math.NumberUtils;
import service.ServiceException;
import ui.GuiException;
import ui.Output;
import ui.controller.AbstractController;
import ui.model.ArticleModel;

import java.io.File;

public class DetailsController extends AbstractController {

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

    public void initializeWith(ArticleModel article, ImageFile imageFile) {
        initializeArticle(article);
        name.setText(this.article.getName());
        price.setText(String.valueOf(this.article.getPrice()));
        description.setText(this.article.getDescription());
        category.setText(this.article.getCategory());
        initializeImage(imageFile);
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
            validateInput();
            setArticleData();
            createNewOrUpdate();
            mainApp.showArticleOverview();
        } catch (GuiException e) {
            logger.error(e);
            Output output = mainApp.getOutput();
            output.showNotification(Alert.AlertType.ERROR, "Error", "Could not save article", e.getMessage());
        } catch (ServiceException e) {
            Output output = mainApp.getOutput();
            output.showExceptionNotification("Error", "Could not save article.", "Please view the log for more details,", e);
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

    private void setArticleData() {
        article.setName(name.getText());
        article.setPrice(Double.parseDouble(price.getText()));
        article.setDescription(description.getText());
        article.setCategory(category.getText());
        article.setImage(imagePath);
    }

    private void createNewOrUpdate() throws ServiceException {
        if (isNew) {
            if (hasChanged) {
                mainApp.getArticleList().get().add(article);
            }
        } else {
            mainApp.getArticleList().update(article);
        }
    }

    private void initializeImage(ImageFile imageFile) {
        image.setImage(imageFile.get(article.getImage()));
        imagePath = this.article.getImage();
    }

    private void initializeArticle(ArticleModel article) {
        if (article == null) {
            this.article = new ArticleModel();
            isNew = true;
        } else {
            this.article = article;
            this.name.disableProperty().set(true);
            isNew = false;
        }
    }


    private boolean discardChanges() {
        if (hasChanged) {
            Output output = new Output();
            return output.showConfirmationDialog("Unsaved Changes", "You have made changes", "Do you want to discard those changes?.");
        }
        return true;
    }

    private void validateInput() throws GuiException {
        validateArticlePrice();
        validateTextFields();
        validateImage();
    }

    private void validateImage() throws GuiException {
        if ("dummyImage.png".equals(imagePath)) {
            throw new GuiException("Please select a new image for this product.");
        }
    }

    private void validateTextFields() throws GuiException {
        if (name.getText().isEmpty()) {
            throw new GuiException("Article name must not be empty.");
        }
        if (category.getText().isEmpty()) {
            throw new GuiException("Article category must not be empty.");
        }
        if (description.getText().isEmpty()) {
            throw new GuiException("Article description must not be empty.");
        }
    }

    private void validateArticlePrice() throws GuiException {
        String priceText = price.getText();
        boolean isNumber = NumberUtils.isNumber(priceText);
        if (priceText.isEmpty() || !isNumber) {
            throw new GuiException("Article price must be a valid number.");
        }
        if (Double.parseDouble(priceText) <= 0) {
            throw new GuiException("Article price must be positive.");
        }
    }

    private void initializeChangeListeners() {
        name.textProperty().addListener((observable, oldValue, newValue) ->
                hasChanged = true);
        category.textProperty().addListener((observable, oldValue, newValue) ->
                hasChanged = true);
        description.textProperty().addListener((observable, oldValue, newValue) ->
                hasChanged = true);
        price.textProperty().addListener((observable, oldValue, newValue) ->
                hasChanged = true);
    }
}
