package ui.controller.article;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import ui.controller.AbstractController;

public class PriceChangeController extends AbstractController {

    private Stage dialogStage;

    public void initializeWith(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    public void handleApply(){

    }

    @FXML
    public void handleClose() {
        dialogStage.close();
    }
}
