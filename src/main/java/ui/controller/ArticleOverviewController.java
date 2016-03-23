package ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import ui.Main;
import ui.model.ArticleModel;

public class ArticleOverviewController extends AbstractController {

    @FXML
    TableColumn<ArticleModel, Image> imageColumn;
    @FXML
    private TableView<ArticleModel> articleTable;
    @FXML
    private TableColumn<ArticleModel, String> nameColumn;
    @FXML
    private TableColumn<ArticleModel, Double> priceColumn;
    @FXML
    private TableColumn<ArticleModel, String> descriptionColumn;
    @FXML
    private TableColumn<ArticleModel, String> categoryColumn;

    public ArticleOverviewController() {

    }

    @Override
    public void setMainApp(Main mainApp) {
        new CustomTableFactory(mainApp).configureArticleTable(articleTable);
        articleTable.setItems(mainApp.getArticleList().get());
        super.setMainApp(mainApp);
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().getCategoryProperty());
    }

    @FXML
    private void handleCreate() {
        mainApp.showArticleDetails(null);
    }


}



