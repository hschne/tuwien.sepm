package entities;

public class ReceiptEntry {

    private Article article;

    private int amount;

    public ReceiptEntry(Article article, int quantity) {
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
}
