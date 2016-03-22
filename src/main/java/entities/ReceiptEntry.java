package entities;

public interface ReceiptEntry {

    Article getArticle();

    void setArticle(Article articleDto);

    int getAmount();

    void setAmount(int amount);

    Receipt getReceipt();

    void setReceipt(Receipt receiptDto);
}
