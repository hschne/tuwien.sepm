package ui.controller.receipt;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ui.controller.AbstractController;
import ui.model.ReceiptModel;

public abstract class AbstractDetailsController extends AbstractController {

    @FXML
    public TextField receiver;
    @FXML
    public TextField receiverAdress;
    @FXML
    public Label date;
    @FXML
    public Button SaveButton;

    protected ReceiptModel receipt;

    protected BorderPane rootLayout;

    public abstract void initializeWith(ReceiptModel receipt);

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    @FXML
    public abstract void handleSave();

    @FXML
    public abstract void handleBack();

}
