package ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.controller.ArticleOverviewController;
import ui.model.ArticleModel;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<ArticleModel> articles = FXCollections.observableArrayList();

    public Main() {
        articles.add(new ArticleModel("Art", 20.0, "Beschreibung", "categpory", "img"));
        articles.add(new ArticleModel("Art1", 20.0, "Beschassssreibung", "catedgpory", "img"));
        articles.add(new ArticleModel("Art2", 20.0, "Beschrhhheibung", "categpory", "img"));
        articles.add(new ArticleModel("Art3", 20.0, "Beschrasdfeibung", "categpory", "img"));
        articles.add(new ArticleModel("Art4", 20.0, "Beschrasdeibung", "cateagpory", "img"));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

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

    public ObservableList<ArticleModel> getArticles() {
        return articles;
    }


}