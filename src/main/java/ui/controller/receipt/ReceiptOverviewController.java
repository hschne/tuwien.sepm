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
import ui.controller.article.ArticleFilterController;
import ui.model.ReceiptModel;

import java.io.IOException;

public class ReceiptOverviewController extends AbstractController {

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

    @FXML
    public void handleCreate() {
        mainApp.showReceiptDetails(null);
    }

    @FXML
    public void handleFilter(){
        if(toggleFilter.isSelected()){
            showFilter();
        }
        else{
            rootLayout.setLeft(null);
        }
    }

    private void showFilter() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/receiptFilter.fxml"));
            AnchorPane receiptFilter = loader.load();
            rootLayout.setLeft(receiptFilter);

            ReceiptFilterController controller = loader.getController();
            controller.initialize(mainApp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
        receiverColumn.setCellValueFactory(cellData -> cellData.getValue().getReceiverProperty());
        totalCostColumn.setCellValueFactory(cellData -> cellData.getValue().getTotalCostProperty().asObject());
    }

}
