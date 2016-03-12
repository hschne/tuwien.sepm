package dao;

import entities.Receipt;

import java.util.List;

public interface ReceiptDao {

    void create(Receipt receipt);

    List<Receipt> readAll();
}
