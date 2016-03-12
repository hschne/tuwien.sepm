package dao;

import entities.Receipt;
import entities.ReceiptEntry;

import java.sql.SQLException;
import java.util.List;

public interface ReceiptDao {

    void create(Receipt receipt) throws SQLException;

    List<Receipt> readAll() throws SQLException;
}
