package service;

import dao.ArticleDao;
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
        ArticleDao articleDao = new ArticleDao(database);
        try {
            articleDao.Create(new Article());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void InitializeService(){

    }
}
