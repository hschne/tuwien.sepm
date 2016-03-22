package ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import ui.Main;
import ui.model.ArticleModel;

public class ArticleOverviewController {

    private Main mainApp;

    @FXML
    private TableView<ArticleModel> articleTable;

    @FXML
    private TableColumn<ArticleModel, String> nameColumn;

    @FXML
    private Text name;
    @FXML
    private Text price;
    @FXML
    private TextArea description;
    @FXML
    private Text category;
    @FXML
    private ImageView image;

    public ArticleOverviewController() {

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        articleTable.setItems(mainApp.getArticles());
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
    }

}
