package entities;

import java.util.Date;
import java.util.List;

/**
 * Implementors represent receipt DTOs
 */
public interface Receipt extends Entity{

    List<ReceiptEntry> getReceiptEntries();

    void setReceiptEntries(List<ReceiptEntry> receiptEntries);

    String getReceiverAddress();

    void setReceiverAddress(String receiverAddress);

    String getReceiver();

    void setReceiver(String receiver);

    Date getDate();

    void setDate(Date date);

}
