package ui.controller.article;

import entities.Article;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import service.ServiceException;
import service.calculation.PriceChange;
import service.criteria.Criteria;
import ui.controller.AbstractController;
import ui.controls.NumberSpinner;
import ui.controls.NumberTextField;
import ui.model.ArticleModel;

import java.util.List;
import java.util.Objects;

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
        List<ArticleModel> articlesToChange = mainApp.getArticleList().get();
        PriceChange priceChange = new PriceChange(mainApp.getArticleRepository(),articlesToChange);
        String changeAmountString = changeAmount.textProperty().getValue();
        Double changeAmountDouble = Double.parseDouble(changeAmountString);
        tryChangePrice(priceChange,changeAmountDouble);
        dialogStage.close();
    }

    private void tryChangePrice(PriceChange priceChange, Double changeAmountDouble) {
        try {
            String changeType = changeAmountType.valueProperty().getValue();
            if(Objects.equals(changeType, "%")){
                priceChange.changeByPercent(changeAmountDouble);
            }
            else{
                priceChange.changeByAbsolute(changeAmountDouble);
            }
        } catch (ServiceException e) {
            mainApp.getOutput().showNotification(Alert.AlertType.ERROR, "Error","Could not change price","Please view logs for more information.");
        }
    }

    private Criteria<Article> buildSelectionCriteria(){
        return null;
    }

    @FXML
    public void handleClose() {
        dialogStage.close();
    }
}
