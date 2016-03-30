package ui.controller;

import dao.h2.ImageFile;
import entities.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.junit.Test;
import org.mockito.Mock;
import service.ServiceException;
import ui.FXTest;
import ui.MainApp;
import ui.Output;
import ui.controller.article.DetailsController;
import ui.model.ArticleList;
import ui.model.ArticleModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailsControllerTest extends FXTest {

    @Mock
    MainApp mockMainApp;

    @Mock
    ArticleList mockArticleList;

    @Mock
    ImageFile mockImageFile;


    @Test
    public void initializeWith_NewArticle_FieldsSet() throws Exception {
        DetailsController controller = new DetailsController();
        InitializeControls(controller);

        controller.initializeWith(null, mockImageFile);

        assertEquals("", controller.name.getText());
        assertEquals("0.0", controller.price.getText());
        assertEquals("", controller.description.getText());
        assertEquals("", controller.category.getText());
    }

    @Test
    public void initializeWith_ExistingArticle_FieldsSet() throws Exception {
        DetailsController controller = new DetailsController();
        ArticleModel model = new ArticleModel("name", 20.0, "description", "category", "img");
        InitializeControls(controller);

        controller.initializeWith(model, mockImageFile);

        assertEquals("name", controller.name.getText());
        assertEquals("20.0", controller.price.getText());
        assertEquals("description", controller.description.getText());
        assertEquals("category", controller.category.getText());
        assertTrue(controller.name.disableProperty().get());
    }

    @Test
    public void handleCancel_CancelCalled_ReturnToOverview() throws Exception {
        DetailsController controller = new DetailsController();
        InitializeControls(controller);

        controller.handleCancel();

        verify(mockMainApp).showArticleOverview();
    }

    @Test
    public void handleSave_SaveNewArticleWithChanges_ArticleListAddCalled() throws Exception {
        DetailsController controller = new DetailsController();
        InitializeControls(controller);
        ObservableList<ArticleModel> observableList = FXCollections.observableArrayList();
        when(mockMainApp.getArticleList()).thenReturn(mockArticleList);
        when(mockArticleList.get()).thenReturn(observableList);

        controller.initializeWith(null, mockImageFile);
        controller.description.setText("Make changes");
        controller.handleSave();

        assertEquals(1,observableList.size());
    }

    @Test
    public void handleSave_SaveExistingArticle_ArticleListAddCalled() throws Exception {
        DetailsController controller = new DetailsController();
        InitializeControls(controller);
        ArticleModel model = new ArticleModel("name", 20.0, "description", "category", "img");
        when(mockMainApp.getArticleList()).thenReturn(mockArticleList);

        controller.initializeWith(model, mockImageFile);
        controller.handleSave();

        verify(mockArticleList).update(model);
    }

    @Test
    public void handleSave_ServiceErrorThrown_NotificationShown() throws Exception {
        DetailsController controller = new DetailsController();
        InitializeControls(controller);
        Output mockOutput = mock(Output.class);
        when(mockMainApp.getArticleList()).thenThrow(ServiceException.class);
        when(mockMainApp.getOutput()).thenReturn(mockOutput);


        controller.initializeWith(new ArticleModel(), mockImageFile);
        controller.handleSave();

        verify(mockOutput).showNotification(eq(Alert.AlertType.ERROR),eq("Error"),anyString(),anyString());
    }



    private void InitializeControls(DetailsController controller) {
        controller.initialize(mockMainApp);
        controller.name = new TextField();
        controller.price = new TextField();
        controller.description = new TextArea();
        controller.category = new TextField();
        controller.image = new ImageView();
    }
}