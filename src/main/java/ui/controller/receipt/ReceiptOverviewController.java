package ui.controller.receipt;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ui.MainApp;
import ui.controller.article.AbstractController;
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
    public void initialize(MainApp mainControllerApp) {
        new CustomReceiptTableFactory(mainControllerApp).configureReceiptTable(receiptTable);
        receiptTable.setItems(mainControllerApp.getReceiptList().get());
        super.initialize(mainControllerApp);
    }



    @FXML
    public void handleCreate() {
        mainApp.showReceiptDetails(null);
    }


    @FXML
    public void initialize() {
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        receiverColumn.setCellValueFactory(cellData -> cellData.getValue().receiverProperty());
        totalCostColumn.setCellValueFactory(cellData -> cellData.getValue().totalCostProperty().asObject());
    }

}
