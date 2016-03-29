package ui.controller.article;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import ui.controller.AbstractController;
import ui.controls.NumberSpinner;
import ui.controls.NumberTextField;

public class PriceChangeController extends AbstractController {

    @FXML
    public ChoiceBox<String> articleSelectionType;
    @FXML
    public NumberTextField articleSelectionThreshold;
    @FXML
    public DatePicker dateSelection;
    @FXML
    public NumberTextField changeAmount;
    @FXML
    ChoiceBox<String> changeAmountType;
    
    private Stage dialogStage;

    public void initializeWith(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    @FXML
    public void initialize() {
        articleSelectionType.setItems(FXCollections.observableArrayList("top sold", "least sold", "sold more than", "sold less than"));
        changeAmountType.setItems(FXCollections.observableArrayList("€","%"));
    }

    @FXML
    public void handleApply() {

    }

    @FXML
    public void handleClose() {
        dialogStage.close();
    }
}
