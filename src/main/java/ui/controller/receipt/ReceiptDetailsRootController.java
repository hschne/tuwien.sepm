package ui.controller.receipt;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import ui.MainApp;
import ui.controller.AbstractController;
import ui.controller.ArticleDetailsController;
import ui.controller.ArticleOverviewController;
import ui.model.ReceiptEntryModel;
import ui.model.ReceiptModel;

import java.io.IOException;

public class ReceiptDetailsRootController extends AbstractController {


    private ReceiptModel receipt;

    @FXML
    public TextField receiver;
    @FXML
    public TextField receiverAdress;
    @FXML
    public Label date;

    @FXML
    public TextField total;

    @FXML
    public TableView<ReceiptEntryModel> receiptEntryTable;
    @FXML
    public TableColumn<ReceiptEntryModel, String> nameColumn;
    @FXML
    public TableColumn<ReceiptEntryModel, Integer> quantityColumn;
    @FXML
    public TableColumn<ReceiptEntryModel, Double> priceColumn;

    @FXML
    private BorderPane rootLayout;


    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().getAmountProperty().asObject());
    }

    public void initializeWith(ReceiptModel receipt) {
        if(receipt != null){
            this.receipt = receipt;
            receiver.setText(this.receipt.getReceiver());
            receiverAdress.setText(String.valueOf(this.receipt.getReceiverAddress()));
            date.setText(this.receipt.getDate().toString());
            total.setText(this.receipt.totalCostProperty().toString());
            viewExistingReceiptEntries(receipt);
        }
    }

    private void viewExistingReceiptEntries(ReceiptModel receipt){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/receiptDetailList.fxml"));
            AnchorPane articleDetails = loader.load();
            rootLayout.setCenter(articleDetails);
            receiptEntryTable.setItems(receipt.getReceiptEntryModels());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
