package ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ui.Main;
import ui.model.ReceiptModel;

public class ReceiptOverviewController extends AbstractController {

    @FXML
    public TableView<ReceiptModel> receiptTable;
    @FXML
    public TableColumn<ReceiptModel, String> dateColumn;
    @FXML
    public TableColumn<ReceiptModel, String> receiverColumn;
    @FXML
    public TableColumn<ReceiptModel, Double> totalCostColumn;

    @Override
    public void initialize(Main mainApp) {
        new CustomReceiptTableFactory(mainApp).configureReceiptTable(receiptTable);
        receiptTable.setItems(mainApp.getReceiptList().get());


        super.initialize(mainApp);
    }

    @FXML
    public void handleCreate() {
        //
    }

    @FXML
    public void initialize() {
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        receiverColumn.setCellValueFactory(cellData -> cellData.getValue().receiverProperty());
        totalCostColumn.setCellValueFactory(cellData -> cellData.getValue().totalCostProperty().asObject());
    }

}
