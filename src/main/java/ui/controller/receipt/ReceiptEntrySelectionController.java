package ui.controller.receipt;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ui.controller.AbstractController;
import ui.controls.CustomReceiptEntryTableFactory;
import ui.model.ArticleModel;
import ui.model.ModelFactory;
import ui.model.ReceiptEntryModel;
import ui.model.ReceiptModel;

import java.util.List;

public class ReceiptEntrySelectionController extends AbstractController {

    @FXML
    public TextField total;
    @FXML
    public TableView<ReceiptEntryModel> receiptEntryTable;
    @FXML
    public TableColumn<ReceiptEntryModel, String> nameColumn;
    @FXML
    public TableColumn<ReceiptEntryModel, Double> priceColumn;
    private ReceiptModel receipt;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
    }

    public void initializeWith(ReceiptModel receipt) {
        this.receipt = receipt;
        new CustomReceiptEntryTableFactory(this).configureReceiptEntryTable(receiptEntryTable);
        List<ArticleModel> articles = mainApp.getArticleList().get();
        ModelFactory modelFactory = new ModelFactory();
        ObservableList<ReceiptEntryModel> receiptEntries = modelFactory.createReceiptEntryModelsFromArticles(articles);
        receiptEntryTable.setItems(receiptEntries);
        total.editableProperty().set(false);
        total.setText("0");
    }

    public void updateTotal() {
        double total = 0;
        for(ReceiptEntryModel entry : receiptEntryTable.getItems()){
            total += entry.getAmount() * entry.getPrice();
        }
        this.receipt.setTotalCost(total);
        this.total.setText(String.valueOf(total));
    }

    public void updateEntry(ReceiptEntryModel entry){
        if(entry.getAmount() == 0){
            receipt.getReceiptEntries().remove(entry);
        }
        else{
            receipt.getReceiptEntries().add(entry);
        }
    }

}
