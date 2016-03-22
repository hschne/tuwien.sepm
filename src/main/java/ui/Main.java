package ui;

import dao.ArticleDao;
import dao.DaoException;
import dao.Database;
import dao.h2.H2ArticleDao;
import dao.h2.H2Database;
import entities.Article;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import service.ArticleRepository;
import service.ServiceException;
import ui.controller.ArticleDetailsController;
import ui.controller.ArticleOverviewController;
import ui.model.ArticleModel;
import ui.model.ModelFactory;

import java.io.IOException;

public class Main extends Application {

    private ArticleRepository articleRepository;

    private ObservableList<ArticleModel> articles = FXCollections.observableArrayList();

    private Stage primaryStage;
    private BorderPane rootLayout;

    public Main() {

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Wendy's Easter Shop");
        initServices();
        initData();

        initRootLayout();

        showPersonOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            rootLayout = FXMLLoader.load(getClass().getResource("/views/root.fxml"));

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/views/articles.fxml"));
            AnchorPane personOverview = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            ArticleOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showArticleDetails(ArticleModel article){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/views/articleDetails.fxml"));
            AnchorPane articleDetails = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(articleDetails);

            // Give the controller access to the main app.
            ArticleDetailsController controller = loader.getController();
            controller.setMainApp(this);
            controller.setArticle(article);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initServices() {
        try {
            H2Database database = new H2Database("/home/hschroedl/sepm");
            ArticleDao articleDao = new H2ArticleDao(database);
            articleRepository = new ArticleRepository(articleDao);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    private void initData(){
        ModelFactory factory = new ModelFactory();
        try {
            articles.addAll(factory.createArticleModels(articleRepository.getAll()));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ArticleModel> getArticles(){
        return articles;
    }


}