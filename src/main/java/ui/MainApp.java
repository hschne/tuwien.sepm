package ui;

import dao.ArticleDao;
import dao.DaoException;
import dao.ReceiptDao;
import dao.h2.H2ArticleDao;
import dao.h2.H2Database;
import dao.h2.H2ReceiptDao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import service.ArticleRepository;
import service.ReceiptRepository;
import service.ServiceException;
import ui.controller.*;
import ui.controller.receipt.ReceiptDetailsRootController;
import ui.controller.receipt.ReceiptOverviewController;
import ui.model.ArticleList;
import ui.model.ArticleModel;
import ui.model.ReceiptList;
import ui.model.ReceiptModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class MainApp extends Application {

    private ArticleList articleList;

    private ReceiptList receiptList;

    private Stage primaryStage;
    private BorderPane rootLayout;


    public MainApp() {

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Wendy's Easter Shop");
        this.primaryStage.getIcons().add(new Image("/icons/application.png"));

        initRootLayout();
        initServices();

        showArticleOverview();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/root.fxml"));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            RootController controller = loader.getController();
            controller.initialize(this);
            primaryStage.show();
        } catch (Exception e) {
            showExceptionNotification("Error","Unexpected error occured", "Please view logs for more information",e);
        }
    }

    public void showArticleOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/articleOverview.fxml"));
            AnchorPane personOverview = loader.load();
            rootLayout.setCenter(personOverview);
            ArticleOverviewController controller = loader.getController();
            controller.initialize(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showArticleDetails(ArticleModel article) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/articleDetails.fxml"));
            AnchorPane articleDetails = loader.load();
            rootLayout.setCenter(articleDetails);
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
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/receiptDetailRoot.fxml"));
            BorderPane rootLayout = loader.load();
            this.rootLayout.setCenter(rootLayout);
            ReceiptDetailsRootController controller = loader.getController();
            controller.initialize(this);
            controller.setRootLayout(rootLayout);
            controller.initializeWith(receipt);
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public void showReceiptOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/receiptOverview.fxml"));
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
            ReceiptDao receiptDao = new H2ReceiptDao(database);
            receiptList = new ReceiptList(new ReceiptRepository(receiptDao));
        } catch (DaoException e) {
            showExceptionNotification("Error", "The database could not be reached.",
                    "Please make sure that it is not currently in use.", e);
        } catch (ServiceException e) {
            showExceptionNotification("Error", "One or more services could not be initialized",
                    "This might be caused by a database error.", e);
        }
    }


    //Stolen from http://code.makery.ch/blog/javafx-dialogs-official/
    private void showExceptionNotification(String title, String header, String content, Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.setResizable(true);
        alert.getDialogPane().setPrefWidth(500);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();
        Label label = new Label("The exception stacktrace was:");
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);


        alert.showAndWait();
    }


}