package ui.controller.article;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import ui.MainApp;
import ui.controller.AbstractController;
import ui.controls.CustomArticleTableFactory;
import ui.model.ArticleModel;

import java.io.IOException;
import java.util.List;

public class OverviewController extends AbstractController {

    @FXML
    public TableView<ArticleModel> articleTable;
    @FXML
    public TableColumn<ArticleModel, String> nameColumn;
    @FXML
    public TableColumn<ArticleModel, Double> priceColumn;
    @FXML
    public TableColumn<ArticleModel, String> descriptionColumn;
    @FXML
    public TableColumn<ArticleModel, String> categoryColumn;
    @FXML
    public BorderPane rootLayout;
    @FXML
    public ToggleButton toggleFilter;

    @Override
    public void initialize(MainApp mainControllerApp) {
        new CustomArticleTableFactory(mainControllerApp).configureArticleTable(articleTable);
        articleTable.setItems(mainControllerApp.getArticleList().get());
        super.initialize(mainControllerApp);
    }

    @FXML
    public void handleCreate() {
        mainApp.showArticleDetails(null);
    }

    @FXML
    public void handleFilter() {
        if (toggleFilter.isSelected()) {
            showFilter();
        } else {
            rootLayout.setLeft(null);
        }
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().getCategoryProperty());
    }

    @FXML
    public void handleStatistics() {
        List<ArticleModel> selected = articleTable.getSelectionModel().getSelectedItems();
        if (selected.isEmpty()) {
            mainApp.showArticleStatistics(articleTable.getItems());
            return;
        }
        mainApp.showArticleStatistics(selected);
    }

    @FXML
    public void handlePriceChange() {
        mainApp.showPriceChange();
    }

    private void showFilter() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/article/articleFilter.fxml"));
            AnchorPane articleFilter = loader.load();
            rootLayout.setLeft(articleFilter);
            FilterController controller = loader.getController();
            controller.initialize(mainApp);
        } catch (IOException e) {
            logger.error(e);
            mainApp.getOutput().showNotification(Alert.AlertType.ERROR, "Error", "Filter view could not be initialized", "Please view the log for details");
        }
    }


}



