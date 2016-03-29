package ui.controller.article;

import entities.Article;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import service.ServiceException;
import service.criteria.article.CategoryCriteria;
import service.criteria.Criteria;
import service.criteria.CriteriaFactory;
import service.criteria.article.NameCriteria;
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

    private final ChangeListener<String> categorySelectionListener = (observable, oldValue, newValue) -> {
        categoryCriteria = new CategoryCriteria(newValue);
        UpdateArticleList();
    };

    private final ChangeListener<String> nameInputListener = (observable, oldValue, newValue) -> {
        nameCriteria = new NameCriteria(newValue);
        UpdateArticleList();
    };

    @FXML
    public void initialize() {
        nameFilter.textProperty().addListener(nameInputListener);
        categoryFilter.valueProperty().addListener(categorySelectionListener);
    }

    @Override
    public void initialize(MainApp mainApp) {
        super.initialize(mainApp);
        categoryFilter.getItems().addAll(getAllCategories());
    }

    @FXML
    public void handleClear() {
        resetName();
        resetCategory();
        try {
            mainApp.getArticleList().applyFilter(new NameCriteria(""));
        } catch (ServiceException e) {
            logger.error(e);
            mainApp.getOutput().showNotification(Alert.AlertType.ERROR, "Error", "Could not clear filters", "Please consult logs for details.");
        }
    }

    private void resetName() {
        nameFilter.textProperty().removeListener(nameInputListener);
        nameFilter.setText("");
        nameFilter.textProperty().addListener(nameInputListener);
        nameCriteria = null;
    }

    private void resetCategory() {
        categoryFilter.valueProperty().removeListener(categorySelectionListener);
        categoryFilter.setValue("");
        categoryFilter.valueProperty().addListener(categorySelectionListener);
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
        if (nameCriteria != null) {
            criteriaFactory.append(nameCriteria);
        }
        if (categoryCriteria != null) {
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
