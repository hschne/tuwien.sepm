package ui.controller;

import javafx.fxml.FXML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ui.Main;

public abstract class AbstractController {

    protected Main mainApp;

    protected final Logger logger = LogManager.getLogger(AbstractController.class);

    public void initialize(Main mainApp) {
        this.mainApp = mainApp;
    }

}
