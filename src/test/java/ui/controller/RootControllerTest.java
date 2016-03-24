package ui.controller;

import org.junit.Test;
import org.mockito.Mock;
import ui.FXTest;
import ui.MainApp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class RootControllerTest extends FXTest {

    @Mock
    MainApp mockMainApp;

    @Test
    public void handleShowArticleOverview_ShowOverview_MainAppShowOverviewCalled() throws Exception {
        RootController controller = new RootController();
        controller.initialize(mockMainApp);

        controller.handleShowArticleOverview();

        verify(mockMainApp).showArticleOverview();
    }

    @Test
    public void handleShowReceiptOverview_ShowOverview_MainAppShowOverviewCalled() throws Exception {
        RootController controller = new RootController();
        controller.initialize(mockMainApp);

        controller.handleShowReceiptOverview();

        verify(mockMainApp).showReceiptOverview();
    }

}