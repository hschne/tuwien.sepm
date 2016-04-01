package ui.controller.receipt;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import ui.model.ReceiptModel;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class ExistingDetailsController extends AbstractDetailsController {


    @Override
    public void initializeWith(ReceiptModel receipt) {
        this.receipt = receipt;
        receiver.setText(this.receipt.getReceiver());
        receiver.setEditable(false);
        receiverAdress.setText(this.receipt.getReceiverAddress());
        receiverAdress.setEditable(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date.setText(dateFormat.format(this.receipt.getDate()));
        saveButton.disableProperty().set(true);
        viewExistingReceiptEntries(receipt);
    }

    @Override
    public void handleSave() {
        //The save button is disabled in this subclass
    }

    @Override
    public void handleBack() {
        mainApp.showReceiptOverview();
    }

    private void viewExistingReceiptEntries(ReceiptModel receipt) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ExistingDetailsController.class.getResource("/views/receipt/receiptEntryList.fxml"));
            AnchorPane articleDetails = loader.load();
            rootLayout.setCenter(articleDetails);
            EntryListController controller = loader.getController();
            controller.initialize(mainApp);
            controller.initializeWith(receipt);
        } catch (IOException e) {
            mainApp.getOutput().showNotification(Alert.AlertType.ERROR, "Error", "Could not load receipt entries", "Please view the logs for details.");
        }
    }

}
