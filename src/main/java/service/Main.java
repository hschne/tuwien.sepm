package service;

import dao.DaoException;
import dao.Database;
import dao.h2.H2ReceiptDao;
import dao.ReceiptDao;
import entities.Receipt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args){
        Database database = null;
        try{
            database = new Database("~/sepm");
        } catch (DaoException e) {
            e.printStackTrace();
        }
        ReceiptDao dao = new H2ReceiptDao(database);
        try {
            List<Receipt> receiptList = dao.readAll();
        } catch (Exception e) {
            logger.error(e);
        }
        finally {
            try{
                database.disconnect();
            }
            catch (DaoException e) {
                e.printStackTrace();
            }
        }
    }

    private static void InitializeService(){

    }
}
