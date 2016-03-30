package ui.controller.receipt;

import entities.Receipt;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import service.ServiceException;
import service.criteria.*;
import service.criteria.operator.DateOperator;
import service.criteria.receipt.DateCriteria;
import service.criteria.receipt.ReceiverCriteria;
import ui.controller.AbstractController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class FilterController extends AbstractController {

    @FXML
    public DatePicker dateFilter;

    private DateCriteria dateCriteria;

    @FXML
    public TextField receiverFilter;

    private ReceiverCriteria receiverCriteria;

    private final ChangeListener<String> nameInputListener = (observable, oldValue, newValue) -> {
        receiverCriteria = new ReceiverCriteria(newValue);
        UpdateReceiptList();
    };

    private final EventHandler<ActionEvent> dateInputListener = (event) -> {
        LocalDate localDate = dateFilter.getValue();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dateCriteria = new DateCriteria(date, DateOperator.AFTER);
        UpdateReceiptList();
    };

    @FXML
    public void initialize() {
        receiverFilter.textProperty().addListener(nameInputListener);
        dateFilter.setOnAction(dateInputListener);
    }


    @FXML
    public void handleClear() {
        resetReceiver();
        resetDate();
        try {
            mainApp.getReceiptList().applyFilter(new ReceiverCriteria(""));
        } catch (ServiceException e) {
            logger.error(e);
            mainApp.getOutput().showNotification(Alert.AlertType.ERROR, "Error", "Could not clear filters", "Please consult logs for details.");
        }
    }

    private void resetDate() {
        dateFilter.getEditor().clear();
        dateCriteria = null;
    }

    private void resetReceiver() {
        receiverFilter.textProperty().removeListener(nameInputListener);
        receiverFilter.setText("");
        receiverFilter.textProperty().addListener(nameInputListener);
        receiverCriteria = null;
    }

    private void UpdateReceiptList() {
        try {
            mainApp.getReceiptList().applyFilter(createCriteria());
        } catch (ServiceException e) {
            logger.error(e);
            mainApp.getOutput().showNotification(Alert.AlertType.ERROR, "Error", "Could not apply filters", "Please consult logs for details.");
        }

    }

    private Criteria<Receipt> createCriteria() {
        CriteriaFactory criteriaFactory = new CriteriaFactory();
        if (receiverCriteria != null) {
            criteriaFactory.append(receiverCriteria);
        }
        if (dateCriteria != null) {
            criteriaFactory.append(dateCriteria);
        }
        return criteriaFactory.get();
    }
}
