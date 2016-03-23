package ui.controller;

import com.sun.prism.impl.Disposer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.util.Callback;
import service.ServiceException;
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
        articleTable.setItems(mainApp.getArticleList().get());
        super.setMainApp(mainApp);
    }

    @FXML
    private void initialize() {
        initializeDoubleClick();
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().getCategoryProperty());

        CreateButton();

    }

    @FXML
    private void handleCreate() {
        mainApp.showArticleDetails(null);
    }

    private void initializeDoubleClick() {
        articleTable.setRowFactory(tv -> {
            TableRow<ArticleModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ArticleModel rowData = row.getItem();
                    mainApp.showArticleDetails(rowData);
                }
            });
            return row;
        });
    }

    private void CreateButton(){
        TableColumn col_action = new TableColumn<>("Action");
        articleTable.getColumns().add(col_action);

        col_action.setCellValueFactory(
                new Callback<CellDataFeatures<Disposer.Record, Boolean>,
                                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(CellDataFeatures<Disposer.Record, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

        //Adding the Button to the cell
        col_action.setCellFactory(
                p -> new ButtonCell());
    }

    //Define the button cell
    private class ButtonCell extends TableCell<Disposer.Record, Boolean> {
        final Button cellButton = new Button("Delete");
        ButtonCell(){
            //Action when the button is pressed
            cellButton.setOnAction(t -> {
                // get Selected Item
                ArticleModel articleModel = (ArticleModel) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                //remove selected item from the table list
                mainApp.getArticleList().get().remove(articleModel);
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
            else{
                setGraphic(null);
            }
        }
    }



}
