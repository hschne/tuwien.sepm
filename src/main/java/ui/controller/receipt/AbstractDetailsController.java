package ui.controller.receipt;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ui.controller.AbstractController;
import ui.model.ReceiptModel;

/**
 * This is just a convenience class. Both the controller for new receipts as well as existing receipts derive from this.
 */
public abstract class AbstractDetailsController extends AbstractController {

    @FXML
    public TextField receiver;
    @FXML
    public TextField receiverAdress;
    @FXML
    public Label date;
    @FXML
    public Button saveButton;

    protected ReceiptModel receipt;

    protected BorderPane rootLayout;

    @FXML
    //Do not remove this. It is actually used, but Idea does not know that!
    public abstract void handleSave();

    @FXML
    //Do not remove this. It is actually used, but Idea does not know that!
    public abstract void handleBack();

    public abstract void initializeWith(ReceiptModel receipt);

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

}
