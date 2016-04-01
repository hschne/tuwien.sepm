package ui.controller.article;

import entities.Article;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.Level;
import service.ServiceException;
import service.calculation.PriceChange;
import service.criteria.Criteria;
import service.criteria.CriteriaFactory;
import service.criteria.article.TimesSoldCriteria;
import service.criteria.article.TimesSoldRelativeCriteria;
import service.criteria.operator.DateOperator;
import service.criteria.operator.NumericOperator;
import service.criteria.operator.RelativeOperator;
import service.decorator.SaleFactory;
import service.decorator.TimedArticleSaleFactory;
import ui.GuiException;
import ui.controller.AbstractController;
import ui.controls.NumberTextField;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Controller for the price change view which is shown in its own window.
 */
public class PriceChangeController extends AbstractController {


    @FXML
    public ChoiceBox<String> articleSelectionType;
    @FXML
    public NumberTextField articleSelectionAmount;
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
        articleSelectionType.setItems(FXCollections.observableArrayList(
                SelectionMode.TOP_SOLD, SelectionMode.LEAST_SOLD, SelectionMode.SOLD_MORE_THAN, SelectionMode.SOLD_LESS_THAN
        ));
        changeAmountType.setItems(FXCollections.observableArrayList("€", "%"));
    }

    /**
     * Apply price changes as configured
     */
    @FXML
    public void handleApply() {
        try {
            validateInput();
            List<Article> articlesToChange = getArticlesToProcess();
            if (shouldProceed(articlesToChange.size())) {
                changePriceFor(articlesToChange);
            }
        } catch (GuiException e) {
            logger.log(Level.ERROR, e);
            mainApp.getOutput().showNotification(Alert.AlertType.ERROR, "Error", "Could not apply price changes.", e.getMessage());
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            mainApp.getOutput().showExceptionNotification("Error", "Could not apply price changes", "Please view the log for more details", e);
        }

    }

    /**
     * Close the dialog window
     */
    @FXML
    public void handleClose() {
        dialogStage.close();
    }

    private void changePriceFor(List<Article> articlesToChange) throws ServiceException {
        PriceChange priceChange = new PriceChange(mainApp.getArticleRepository(), articlesToChange);
        String changeAmountString = changeAmount.textProperty().getValue();
        Double changeAmountDouble = Double.parseDouble(changeAmountString);
        tryChangePrice(priceChange, changeAmountDouble);
        dialogStage.close();
    }

    private boolean shouldProceed(int articlesChanged) {
        return mainApp.getOutput().showConfirmationDialog("Confirmation", "Are you sure you want to proceed?", articlesChanged + " Articles will be changed!");
    }

    private List<Article> getArticlesToProcess() throws GuiException, ServiceException {
        List<? extends Article> articles = mainApp.getArticleList().get();
        Criteria<Article> selectionCriteria = buildSelectionCriteria();
        return selectionCriteria.apply(articles);
    }

    private void validateInput() throws GuiException {
        validateSelection();
        validateChange();
    }

    private void validateChange() throws GuiException {
        String changeType = changeAmountType.getSelectionModel().getSelectedItem();
        if (changeType == null) {
            throw new GuiException("Invalid change type");
        }
        String changeAmountNumber = changeAmount.getText();
        if (changeAmountNumber.isEmpty() || !NumberUtils.isNumber(changeAmountNumber)) {
            throw new GuiException("Invalid change amount");
        }
    }

    private void validateSelection() throws GuiException {
        String selectionType = articleSelectionType.getSelectionModel().getSelectedItem();
        if (selectionType == null) {
            throw new GuiException("Invalid article selection mode");
        }
        String articleSelectionNumber = articleSelectionAmount.getText();
        boolean isNumber = NumberUtils.isNumber(articleSelectionNumber);
        boolean isZero = Objects.equals(articleSelectionNumber, "0");
        if (articleSelectionNumber.isEmpty() || !isNumber || isZero) {
            throw new GuiException("Invalid article selection quantity");
        }
        LocalDate localDate = dateSelection.getValue();
        if (localDate == null) {
            throw new GuiException("Invalid date selection");
        }
    }

    private void tryChangePrice(PriceChange priceChange, Double changeAmountDouble) throws ServiceException {
        String changeType = changeAmountType.valueProperty().getValue();
        if (Objects.equals(changeType, "%")) {
            priceChange.changeByPercent(changeAmountDouble);
        } else {
            priceChange.changeByAbsolute(changeAmountDouble);
        }

    }

    private Criteria<Article> buildSelectionCriteria() throws GuiException {
        CriteriaFactory criteriaFactory = new CriteriaFactory();
        LocalDate localDate = dateSelection.getValue();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        SaleFactory saleFactory = new TimedArticleSaleFactory(mainApp.getReceiptRepository(), date, DateOperator.AFTER);
        Criteria<Article> articleSalesCriteria = createArticleSelectionCriteria(saleFactory);
        criteriaFactory.append(articleSalesCriteria);
        return criteriaFactory.get();
    }

    private Criteria<Article> createArticleSelectionCriteria(SaleFactory saleFactory) throws GuiException {
        String articleSelectionMode = articleSelectionType.getValue();
        int articleSelectionNumber = Integer.parseInt(articleSelectionAmount.getText());
        Criteria<Article> articleSalesCriteria;
        switch (articleSelectionMode) {
            case SelectionMode.TOP_SOLD:
                articleSalesCriteria = new TimesSoldRelativeCriteria(saleFactory, articleSelectionNumber, RelativeOperator.TOP);
                break;
            case SelectionMode.LEAST_SOLD:
                articleSalesCriteria = new TimesSoldRelativeCriteria(saleFactory, articleSelectionNumber, RelativeOperator.BOTTOM);
                break;
            case SelectionMode.SOLD_MORE_THAN:
                articleSalesCriteria = new TimesSoldCriteria(saleFactory, articleSelectionNumber, NumericOperator.GREATER);
                break;
            case SelectionMode.SOLD_LESS_THAN:
                articleSalesCriteria = new TimesSoldCriteria(saleFactory, articleSelectionNumber, NumericOperator.LOWER);
                break;
            default:
                throw new GuiException("Invalid article selection mode");
        }
        return articleSalesCriteria;
    }

    private static class SelectionMode {

        public static final String TOP_SOLD = "Top sold";

        public static final String LEAST_SOLD = "Least sold";

        public static final String SOLD_MORE_THAN = "Sold more than";

        public static final String SOLD_LESS_THAN = "Sold less than";
    }
}
