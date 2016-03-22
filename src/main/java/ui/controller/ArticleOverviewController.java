package ui.controller;

import entities.Article;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import service.AbstractService;
import ui.Main;
import ui.model.ArticleModel;

public class ArticleOverviewController extends AbstractController {

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

    @FXML TableColumn<ArticleModel, Image> imageColumn;

    public ArticleOverviewController() {

    }

    @Override
    public void setMainApp(Main mainApp) {
        articleTable.setItems(mainApp.getArticles());
        super.setMainApp(mainApp);
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.

        initializeDoubleClick();

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().getCategoryProperty());

    }

    private void initializeDoubleClick() {
        articleTable.setRowFactory( tv -> {
            TableRow<ArticleModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    ArticleModel rowData = row.getItem();
                    showArticleDetails(rowData);
                }
            });
            return row ;
        });
    }

    private void showArticleDetails(ArticleModel article){
        mainApp.showArticleDetails(article);
    }




}
