package service;

import dao.H2ArticleDao;
import dao.Database;
import entities.Article;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

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
        H2ArticleDao articleDao = new H2ArticleDao(database);
        try {
            Article art = new Article(4,"name",20.0,"desc","im","dudue");
            System.out.println(art.toString());
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
