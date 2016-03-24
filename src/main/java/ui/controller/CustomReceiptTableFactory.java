package ui.controller;

import com.sun.prism.impl.Disposer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.util.Callback;
import ui.Main;
import ui.model.ArticleModel;
import ui.model.ReceiptModel;

public class CustomReceiptTableFactory {

    private Main mainApp;

    public CustomReceiptTableFactory(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void configureReceiptTable(TableView<ReceiptModel> tableView) {
        initializeDoubleClick(tableView);
        createViewButton(tableView);
    }

    private void initializeDoubleClick(TableView articleTable) {
        articleTable.setRowFactory(tv -> {
            TableRow<ReceiptModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ReceiptModel rowData = row.getItem();
                    mainApp.showReceiptDetails(rowData);
                }
            });
            return row;
        });
    }

    private void createViewButton(TableView articleTable) {
        TableColumn col_action = new TableColumn<>("");
        articleTable.getColumns().add(col_action);
        col_action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>,
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

        //Adding the Button to the cell
        col_action.setCellFactory(
                p -> new ViewButtonCell());
    }



    //Define the button cell
    private class ViewButtonCell extends TableCell<Disposer.Record, Boolean> {
        final Button cellButton = new Button("View");

        ViewButtonCell() {
            cellButton.setOnAction(t -> {
                ReceiptModel receiptModel = (ReceiptModel) ViewButtonCell.this.
                        getTableView().getItems().get(ViewButtonCell.this.getIndex());
                mainApp.showReceiptDetails(receiptModel);

            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            } else {
                setGraphic(null);
            }
        }
    }
}
