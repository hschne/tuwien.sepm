package ui.controller.article;

import entities.Article;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import service.ServiceException;
import service.criteria.Criteria;
import service.criteria.NameCriteria;
import ui.controller.AbstractController;

public class ArticleFilterController extends AbstractController {

    @FXML
    public TextField nameFilter;

    @FXML
    public void initialize() {
        nameFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            UpdateArticleList(new NameCriteria(nameFilter.getText()));
        });
    }

    private void UpdateArticleList(Criteria<Article> criteria) {
        try {
            mainApp.getArticleList().applyFilter(criteria);
        } catch (ServiceException e) {
            mainApp.getOutput().showNotification(Alert.AlertType.ERROR, "Error", "Could not apply filters", "Please consult logs for details.");
        }
    }
}
