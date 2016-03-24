package ui;

import dao.ArticleDao;
import dao.DaoException;
import dao.h2.H2ArticleDao;
import dao.h2.H2Database;
import entities.Receipt;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import service.ArticleRepository;
import service.ServiceException;
import ui.controller.ArticleDetailsController;
import ui.controller.ArticleOverviewController;
import ui.controller.ReceiptOverviewController;
import ui.model.ArticleList;
import ui.model.ArticleModel;
import ui.model.ReceiptList;
import ui.model.ReceiptModel;

import javax.swing.*;
import java.io.IOException;

public class Main extends Application {

    private ArticleList articleList;

    private ReceiptList receiptList;

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


        initRootLayout();
        initServices();
        initData();

        showArticleOverview();
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
    public void showArticleOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/views/articleOverview.fxml"));
            AnchorPane personOverview = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            ArticleOverviewController controller = loader.getController();
            controller.initialize(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showArticleDetails(ArticleModel article) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/views/articleDetails.fxml"));
            AnchorPane articleDetails = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(articleDetails);

            // Give the controller access to the main app.
            ArticleDetailsController controller = loader.getController();
            controller.initialize(this);
            controller.initializeWith(article);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showNotification(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert alert = new Alert(type);
        alert.initOwner(primaryStage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    public ArticleList getArticleList() {
        return articleList;
    }

    public void showReceiptDetails(ReceiptModel receipt) {

    }

    public void showReceiptOverview(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/views/receiptOverview.fxml"));
            AnchorPane receiptOverview = loader.load();
            rootLayout.setCenter(receiptOverview);
            ReceiptOverviewController controller = loader.getController();
            controller.initialize(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ReceiptList getReceiptList() {
        return receiptList;
    }

    private void initServices() {
        try {
            H2Database database = new H2Database("/home/hschroedl/sepm");
            ArticleDao articleDao = new H2ArticleDao(database);
            articleList = new ArticleList(new ArticleRepository(articleDao));
        } catch (DaoException e) {
            showNotification(Alert.AlertType.ERROR, "Critical error", "Database error", "Woat");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void initData() {

    }


}