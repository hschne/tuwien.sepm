package ui.controller.receipt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import ui.MainApp;
import ui.controller.AbstractController;
import ui.controls.CustomReceiptTableFactory;
import ui.model.ReceiptModel;

import java.io.IOException;

/**
 * Controller for receipt overview. Has functionality for creating new ones and viewing details.
 */
public class OverviewController extends AbstractController {

    @FXML
    public BorderPane rootLayout;
    @FXML
    public ToggleButton toggleFilter;
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

    /**
     * Creates a new receipt
     */
    @FXML
    public void handleCreate() {
        mainApp.showReceiptDetails(null);
    }

    /**
     * Displays the filter view.
     */
    @FXML
    public void handleFilter() {
        if (toggleFilter.isSelected()) {
            showFilter();
        } else {
            rootLayout.setLeft(null);
        }
    }

    @FXML
    public void initialize() {
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
        receiverColumn.setCellValueFactory(cellData -> cellData.getValue().getReceiverProperty());
        totalCostColumn.setCellValueFactory(cellData -> cellData.getValue().getTotalCostProperty().asObject());
    }

    private void showFilter() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/receipt/receiptFilter.fxml"));
            AnchorPane receiptFilter = loader.load();
            rootLayout.setLeft(receiptFilter);

            FilterController controller = loader.getController();
            controller.initialize(mainApp);
        } catch (IOException e) {
            logger.error(e);
            mainApp.getOutput().showExceptionNotification("Error", "Could not load filter view", "Please view logs for more details", e);
        }
    }

}
