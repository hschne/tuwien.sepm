package ui.model;

import entities.Receipt;
import entities.ReceiptEntry;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.util.Date;
import java.util.List;

public class ReceiptModel implements Receipt {

    private final SimpleStringProperty date;

    private SimpleStringProperty receiver;

    private SimpleStringProperty receiverAdress;

    private SimpleDoubleProperty totalCost;

    private List<ReceiptEntry> receiptEntries;

    private ObservableList<ReceiptEntryModel> receiptEntryModels;

    private ModelFactory modelFactory;

    private int Id;

    public ReceiptModel(String date, String receiver, String receiverAdress, double totalCost, List<ReceiptEntry> receiptEntries) {
        this.date = new SimpleStringProperty(date);
        this.receiver = new SimpleStringProperty(receiver);
        this.receiverAdress = new SimpleStringProperty(receiverAdress);
        this.totalCost = new SimpleDoubleProperty(totalCost);
        modelFactory = new ModelFactory();
        this.receiptEntries = receiptEntries;
        this.receiptEntryModels = modelFactory.createReceiptEntryModels(receiptEntries);
    }

    public ObservableList<ReceiptEntryModel> getReceiptEntryModels() {
        return this.receiptEntryModels;
    }

    @Override
    public List<ReceiptEntry> getReceiptEntries() {
        return this.receiptEntries;
    }

    @Override
    public void setReceiptEntries(List<ReceiptEntry> receiptEntries) {
        // Not supported
    }

    @Override
    public String getReceiverAddress() {
        return receiverAdress.getValue();
    }

    @Override
    public void setReceiverAddress(String receiverAddress) {
        // Not supported
    }

    @Override
    public String getReceiver() {
        return receiver.getValue();
    }

    @Override
    public void setReceiver(String receiver) {
        //Not supported
    }

    @Override
    public Date getDate() {
        return new Date(date.getValue());
    }

    @Override
    public void setDate(Date date) {
        //
    }

    public SimpleStringProperty receiverProperty() {
        return receiver;
    }

    public SimpleStringProperty receiverAdressProperty() {
        return receiverAdress;
    }

    public SimpleDoubleProperty totalCostProperty() {
        return totalCost;
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    @Override
    public int getId() {
        return Id;
    }

    @Override
    public void setId(int id) {
        Id = id;
    }
}
