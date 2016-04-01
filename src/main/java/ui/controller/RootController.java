package ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import static java.lang.System.*;

/**
 * The root controller manages the menu bar on top of the window
 */
public class RootController extends AbstractController {

    /**
     * Displays the article overview.
     */
    @FXML
    public void handleShowArticleOverview(){
        mainApp.showArticleOverview();
    }

    /**
     * Displays the receipt overview.
     */
    @FXML
    public void handleShowReceiptOverview(){
        mainApp.showReceiptOverview();
    }


    /**
     * Displays the about dialog.
     */
    @FXML
    public void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Wendy's Easter Shop\n" +
                "Version 1.0\n");
        alert.setContentText("Author: Hans-Jörg Schrödl" +
                "\nWebsite: hschroedl.at");

        alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    public void handleExit() {
        exit(0);
    }
}