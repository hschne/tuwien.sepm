package entities;

public class ReceiptEntryDto extends Dto implements ReceiptEntry {

    private Receipt receiptDto;
    private Article articleDto;

    private int amount;

    public ReceiptEntryDto(Receipt receiptDto, Article articleDto, int quantity) {
        this.receiptDto = receiptDto;
        this.articleDto = articleDto;
        this.amount = quantity;
    }

    public Article getArticle() {
        return articleDto;
    }

    public void setArticle(Article articleDto) {
        this.articleDto = articleDto;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Receipt getReceipt() {
        return receiptDto;
    }

    public void setReceipt(Receipt receiptDto) {
        this.receiptDto = receiptDto;
    }
}
