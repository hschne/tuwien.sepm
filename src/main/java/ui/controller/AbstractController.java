package ui.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ui.MainApp;

/**
 * Convenience class for all controllers.
 * All controllers hold a reference to the main app class.
 */
public abstract class AbstractController {

    protected final Logger logger = LogManager.getLogger(AbstractController.class);
    protected MainApp mainApp;

    public void initialize(MainApp mainControllerApp) {
        this.mainApp = mainControllerApp;
    }


}
