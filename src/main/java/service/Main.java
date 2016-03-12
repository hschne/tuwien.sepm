package service;

import dao.H2ArticleDao;
import dao.Database;
import dao.H2ReceiptDao;
import dao.ReceiptDao;
import entities.Article;
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
        } catch (ClassNotFoundException e) {
            logger.error(e);
            return;
        } catch (SQLException e) {
            logger.error(e);
            return;
        }
        ReceiptDao dao = new H2ReceiptDao(database);
        try {
            List<Receipt> receiptList = dao.readAll();
        } catch (Exception e) {
            logger.error(e);
        }
        finally {
            database.Disconnect();
        }
    }

    private static void InitializeService(){

    }
}
