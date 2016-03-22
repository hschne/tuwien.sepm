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


    public List<ReceiptEntry> getReceiptEntries() {
        return receiptEntries;
    }

    @Override
    public void setReceiptEntries(List<ReceiptEntry> receiptEntries) {
        this.receiptEntries = receiptEntries;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
