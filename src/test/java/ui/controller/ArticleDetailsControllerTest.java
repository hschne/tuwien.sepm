package ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.junit.Test;
import org.mockito.Mock;
import service.ServiceException;
import ui.FXTest;
import ui.Main;
import ui.model.ArticleList;
import ui.model.ArticleModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ArticleDetailsControllerTest extends FXTest {

    @Mock
    Main mockMainApp;

    @Mock
    ArticleList mockArticleList;

    @Test
    public void initializeWith_NewArticle_FieldsSet() throws Exception {
        ArticleDetailsController controller = new ArticleDetailsController();
        InitializeControls(controller);

        controller.initializeWith(null);

        assertEquals("", controller.name.getText());
        assertEquals("0.0", controller.price.getText());
        assertEquals("", controller.description.getText());
        assertEquals("", controller.category.getText());
    }

    @Test
    public void initializeWith_ExistingArticle_FieldsSet() throws Exception {
        ArticleDetailsController controller = new ArticleDetailsController();
        ArticleModel model = new ArticleModel("name", 20.0, "description", "category", "img");
        InitializeControls(controller);

        controller.initializeWith(model);

        assertEquals("name", controller.name.getText());
        assertEquals("20.0", controller.price.getText());
        assertEquals("description", controller.description.getText());
        assertEquals("category", controller.category.getText());
        assertTrue(controller.name.disableProperty().get());
    }

    @Test
    public void handleCancel_CancelCalled_ReturnToOverview() throws Exception {
        ArticleDetailsController controller = new ArticleDetailsController();
        InitializeControls(controller);

        controller.handleCancel();

        verify(mockMainApp).showArticleOverview();
    }

    @Test
    public void handleSave_SaveNewArticle_ArticleListAddCalled() throws Exception {
        ArticleDetailsController controller = new ArticleDetailsController();
        InitializeControls(controller);
        ObservableList<ArticleModel> observableList = FXCollections.observableArrayList();
        when(mockMainApp.getArticleList()).thenReturn(mockArticleList);
        when(mockArticleList.get()).thenReturn(observableList);

        controller.initializeWith(null);
        controller.handleSave();

        assertEquals(1,observableList.size());
    }

    @Test
    public void handleSave_SaveExistingArticle_ArticleListAddCalled() throws Exception {
        ArticleDetailsController controller = new ArticleDetailsController();
        InitializeControls(controller);
        ArticleModel model = new ArticleModel("name", 20.0, "description", "category", "img");
        when(mockMainApp.getArticleList()).thenReturn(mockArticleList);

        controller.initializeWith(model);
        controller.handleSave();

        verify(mockArticleList).update(model);
    }

    @Test
    public void handleSave_ServiceErrorThrown_NotificationShown() throws Exception {
        ArticleDetailsController controller = new ArticleDetailsController();
        InitializeControls(controller);
        when(mockMainApp.getArticleList()).thenReturn(mockArticleList);
        when(mockArticleList.get()).thenThrow(ServiceException.class);

        controller.initializeWith(null);
        controller.handleSave();

        verify(mockMainApp).showNotification(eq(Alert.AlertType.ERROR),eq("Error"),anyString(),anyString());

    }



    private void InitializeControls(ArticleDetailsController controller) {
        controller.initialize(mockMainApp);
        controller.name = new TextField();
        controller.price = new TextField();
        controller.description = new TextArea();
        controller.category = new TextField();
    }
}