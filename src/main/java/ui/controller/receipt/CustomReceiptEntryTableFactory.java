package ui.controller.receipt;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import ui.model.ArticleModel;
import ui.model.ReceiptEntryModel;

public class CustomReceiptEntryTableFactory {

    private ReceiptEntrySelectionController controller;

    public CustomReceiptEntryTableFactory(ReceiptEntrySelectionController controller) {
        this.controller = controller;
    }

    public void configureReceiptEntryTable(TableView<ReceiptEntryModel> tableView) {
        tableView.setEditable(true);
        createChangeAmountEdit(tableView);

    }

    private void createChangeAmountEdit(TableView<ReceiptEntryModel> tableView) {
        TableColumn col_action = new TableColumn<>("");
        col_action.setPrefWidth(70);
        col_action.setMinWidth(70);
        col_action.setMaxWidth(70);
        tableView.getColumns().add(col_action);
        //Adding the Button to the cell
        col_action.setCellFactory(
                p -> new EditingCell());
        col_action.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ReceiptEntryModel, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ReceiptEntryModel, String> t) {
                        int integer = Integer.parseInt(t.getNewValue());
                        ReceiptEntryModel model = t.getTableView().getItems().get(
                                t.getTablePosition().getRow());
                        model.setAmount(integer);
                        controller.updateTotal();
                        controller.updateEntry(model);
                    }
                }
        );
    }
    class EditingCell extends TableCell<ArticleModel, String> {

        private TextField textField;

        public EditingCell() {
        }


        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText(getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.focusedProperty().addListener((arg0, arg1, arg2) -> {
                if (!arg2) {
                    commitEdit(textField.getText());
                }
            });
            textField.setOnKeyPressed(ke -> {

                if (ke.getCode().equals(KeyCode.ENTER))
                    updateItem(getItem(),isEmpty());
            });
        }

        private String getString() {
            String getItem = getItem();
            return getItem() == null || isEmpty() ? "0" : getItem();
        }

    }

}




