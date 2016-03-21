package entities;

public class ReceiptEntry extends AbstractEntity {

    private Receipt receipt;
    private Article article;

    private int amount;

    public ReceiptEntry(Receipt receipt, Article article, int quantity) {
        this.receipt = receipt;
        this.article = article;
        this.amount = quantity;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }
}
