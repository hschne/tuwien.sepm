package ui.controller.article;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import ui.MainApp;
import ui.model.ArticleModel;

public class ArticleOverviewController extends AbstractController {

    @FXML
    private TableColumn<ArticleModel, Image> imageColumn;
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

    public ArticleOverviewController() {

    }

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
    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().getCategoryProperty());
    }


}



