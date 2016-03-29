package ui.controller.article;

import entities.Article;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import service.ServiceException;
import service.criteria.*;
import ui.MainApp;
import ui.controller.AbstractController;
import ui.model.ArticleModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArticleFilterController extends AbstractController {

    @FXML
    public TextField nameFilter;

    @FXML
    ComboBox<String> categoryFilter;

    private Criteria<Article> nameCriteria;

    private Criteria<Article> categoryCriteria;

    private ChangeListener<String> comboBoxListener = (observable, oldValue, newValue) -> {
        categoryCriteria = new CategoryCriteria(newValue);
        UpdateArticleList();
    };

    @FXML
    public void initialize() {
        nameFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            nameCriteria = new NameCriteria(newValue);
            UpdateArticleList();
        });
        categoryFilter.valueProperty().addListener(comboBoxListener);
    }

    @Override
    public void initialize(MainApp mainApp){
        super.initialize(mainApp);
        categoryFilter.getItems().addAll(getAllCategories());
    }

    @FXML
    public void handleClear() {
        nameCriteria = null;
        resetCategory();
        try {
            mainApp.getArticleList().applyFilter(new NameCriteria(""));
        } catch (ServiceException e) {
            logger.error(e);
            mainApp.getOutput().showNotification(Alert.AlertType.ERROR, "Error", "Could not clear filters", "Please consult logs for details.");
        }
    }

    private void resetCategory() {
        categoryFilter.valueProperty().removeListener(comboBoxListener);
        categoryFilter.setValue("");
        categoryFilter.valueProperty().addListener(comboBoxListener);
        categoryCriteria = null;
    }

    private List<String> getAllCategories() {
        ObservableList<ArticleModel> articles = mainApp.getArticleList().get();
        Set<String> categories = articles.stream().map(Article::getCategory).collect(Collectors.toSet());
        List<String> result = new ArrayList<>();
        result.addAll(categories);
        Collections.sort(result);
        return result;
    }

    private Criteria<Article> createCriteria() {
        CriteriaFactory criteriaFactory = new CriteriaFactory();
        if(nameCriteria != null){
            criteriaFactory.append(nameCriteria);
        }
        if(categoryCriteria != null){
            criteriaFactory.append(categoryCriteria);
        }
        return criteriaFactory.get();
    }

    private void UpdateArticleList() {
        try {
            mainApp.getArticleList().applyFilter(createCriteria());
        } catch (ServiceException e) {
            logger.error(e);
            mainApp.getOutput().showNotification(Alert.AlertType.ERROR, "Error", "Could not apply filters", "Please consult logs for details.");
        }
    }
}
