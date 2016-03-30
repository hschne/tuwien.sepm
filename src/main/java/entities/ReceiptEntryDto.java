package entities;

public class ReceiptEntryDto extends Dto implements ReceiptEntry {

    private Receipt receipt;
    private Article article;

    private int amount;

    public ReceiptEntryDto(Receipt receipt, Article article, int quantity) {
        this.receipt = receipt;
        this.article = article;
        this.amount = quantity;
    }

    @Override
    public Article getArticle() {
        return article;
    }

    @Override
    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public Receipt getReceipt() {
        return receipt;
    }

    @Override
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }
}
