package entities;

import java.util.Date;
import java.util.List;

public class ReceiptDto extends Dto implements Receipt {

    private int id;

    private Date date;
    private String receiver;
    private String receiverAddress;
    private List<ReceiptEntry> receiptEntries;

    public ReceiptDto() {
        //Strictly used for unit tests
    }

    public ReceiptDto(int id, Date date, String receiver, String receiverAddress, List<ReceiptEntry> receiptEntries) {
        this(date, receiver, receiverAddress, receiptEntries);
        this.id = id;

    }

    public ReceiptDto(Date date, String receiver, String receiverAddress, List<ReceiptEntry> receiptEntries) {
        this.date = date;
        this.receiver = receiver;
        this.receiverAddress = receiverAddress;
        this.receiptEntries = receiptEntries;
    }


    @Override
    public List<ReceiptEntry> getReceiptEntries() {
        return receiptEntries;
    }

    @Override
    public void setReceiptEntries(List<ReceiptEntry> receiptEntries) {
        this.receiptEntries = receiptEntries;
    }

    @Override
    public String getReceiverAddress() {
        return receiverAddress;
    }

    @Override
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    @Override
    public String getReceiver() {
        return receiver;
    }

    @Override
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

}
