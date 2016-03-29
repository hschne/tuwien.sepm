package ui.controller.article;

import com.sun.prism.impl.Disposer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.util.Callback;
import ui.MainApp;
import ui.Output;
import ui.model.ArticleModel;

public class CustomArticleTableFactory {


    private MainApp mainControllerApp;

    public CustomArticleTableFactory(MainApp mainControllerApp) {
        this.mainControllerApp = mainControllerApp;
    }

    public void configureArticleTable(TableView<ArticleModel> tableView) {
        initializeDoubleClick(tableView);
        createEditButton(tableView);
        createDeleteButton(tableView);
    }

    private void initializeDoubleClick(TableView articleTable) {
        articleTable.setRowFactory(tv -> {
            TableRow<ArticleModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ArticleModel rowData = row.getItem();
                    mainControllerApp.showArticleDetails(rowData);
                }
            });
            return row;
        });
    }

    private void createEditButton(TableView articleTable) {
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
                p -> new EditButtonCell());
    }

    private void createDeleteButton(TableView articleTable) {
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
                p -> new DeleteButtonCell());
    }

    //Define the button cell
    private class DeleteButtonCell extends TableCell<Disposer.Record, Boolean> {
        final Button cellButton = new Button("Delete");

        DeleteButtonCell() {
            cellButton.setOnAction(t -> {
                ArticleModel articleModel = (ArticleModel) DeleteButtonCell.this.
                        getTableView().getItems().get(DeleteButtonCell.this.getIndex());
                RemoveArticle(articleModel);
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

    private void RemoveArticle(ArticleModel articleModel) {
        Output output = mainControllerApp.getOutput();
        boolean shouldDelete = output.showConfirmationDialog("Delete article", "Are you sure you want to delete this article?", "This action can not be undone.");
        if(shouldDelete){
            mainControllerApp.getArticleList().get().remove(articleModel);
        }
    }

    //Define the button cell
    private class EditButtonCell extends TableCell<Disposer.Record, Boolean> {
        final Button cellButton = new Button("Edit");

        EditButtonCell() {
            cellButton.setOnAction(t -> {
                ArticleModel articleModel = (ArticleModel) EditButtonCell.this.
                        getTableView().getItems().get(EditButtonCell.this.getIndex());
                mainControllerApp.showArticleDetails(articleModel);

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
