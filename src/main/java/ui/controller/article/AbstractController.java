package ui.controller.article;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ui.MainApp;

public abstract class AbstractController {

    protected MainApp mainApp;

    protected final Logger logger = LogManager.getLogger(AbstractController.class);

    public void initialize(MainApp mainControllerApp) {
        this.mainApp = mainControllerApp;
    }

}
