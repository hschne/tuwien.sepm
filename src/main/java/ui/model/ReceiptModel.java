package ui.model;

import entities.Receipt;
import entities.ReceiptEntry;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReceiptModel implements Receipt {

    private final SimpleStringProperty dateProperty;

    private SimpleStringProperty receiver;

    private SimpleStringProperty receiverAdress;

    private SimpleDoubleProperty totalCost;

    private List<ReceiptEntry> receiptEntries;


    private ModelFactory modelFactory;

    private Date date;

    private int Id;

    public ReceiptModel(Date date, String receiver, String receiverAdress, double totalCost, List<ReceiptEntry> receiptEntries) {
        this.date = date;
        this.dateProperty = new SimpleStringProperty(new SimpleDateFormat("dd/MM/yyyy").format(date));
        this.receiver = new SimpleStringProperty(receiver);
        this.receiverAdress = new SimpleStringProperty(receiverAdress);
        this.totalCost = new SimpleDoubleProperty(totalCost);
        modelFactory = new ModelFactory();
        this.receiptEntries = receiptEntries;
    }

    public ObservableList<ReceiptEntryModel> getReceiptEntryModels() {
        return modelFactory.createReceiptEntryModels(receiptEntries);
    }

    @Override
    public List<ReceiptEntry> getReceiptEntries() {
        return this.receiptEntries;
    }

    @Override
    public void setReceiptEntries(List<ReceiptEntry> receiptEntries) {
        this.receiptEntries = receiptEntries;
    }

    @Override
    public String getReceiverAddress() {
        return receiverAdress.getValue();
    }

    @Override
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAdress.setValue(receiverAddress);
    }

    @Override
    public String getReceiver() {
        return receiver.getValue();
    }

    @Override
    public void setReceiver(String receiver) {
        this.receiver.setValue(receiver);
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    public SimpleStringProperty getReceiverProperty() {
        return receiver;
    }

    public SimpleStringProperty getReceiverAdressProperty() {
        return receiverAdress;
    }

    public SimpleDoubleProperty getTotalCostProperty() {
        return totalCost;
    }

    public void setTotalCost(Double newTotal) {
        totalCost.setValue(newTotal);
    }

    public SimpleStringProperty getDateProperty() {
        return dateProperty;
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
