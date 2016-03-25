package ui.controller.receipt;

import entities.ReceiptEntry;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import ui.model.ReceiptModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class NewReceiptDetailsController extends AbstractReceiptDetailsController {


    @Override
    public void initializeWith(ReceiptModel receipt) {
        this.receipt = new ReceiptModel(new Date(), "", "", 0.0, new ArrayList<>());
        receiver.setText(this.receipt.getReceiver());
        receiverAdress.setText(this.receipt.getReceiverAddress());
        date.setText(this.receipt.getDateProperty().getValue());
        viewReceiptEntrySelection();
    }

    @Override
    public void handleSave() {
        receipt.setReceiver(receiver.getText());
        receipt.setReceiverAddress(receiverAdress.getText());
        mainApp.getReceiptList().get().add(receipt);
    }

    @Override
    public void handleBack() {
        mainApp.showReceiptOverview();
    }


    private void viewReceiptEntrySelection() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ExistingReceiptDetailsController.class.getResource("/views/receiptEntrySelection.fxml"));
            AnchorPane articleDetails = loader.load();
            rootLayout.setCenter(articleDetails);
            ReceiptEntrySelectionController controller = loader.getController();
            controller.initialize(mainApp);
            controller.initializeWith(this.receipt);
        } catch (IOException e) {
            mainApp.showNotification(Alert.AlertType.ERROR, "Error", "Could not load receipt entries", "Please view the logs for details.");
        }
    }
}
