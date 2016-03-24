package ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.junit.Test;
import org.mockito.Mock;
import ui.FXTest;
import ui.MainApp;
import ui.model.ArticleList;
import ui.model.ArticleModel;
import ui.model.ModelFactory;

import static base.DummyEntityFactory.createDummyArticles;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ArticleOverviewControllerTest extends FXTest {


    @Mock
    MainApp mockMainControllerApp;
    @Mock
    ArticleList mockArticleList;

    @Test
    public void initialize_InitializeWithMainApp_ArticleTableFilled() throws Exception {
        ArticleOverviewController controller = new ArticleOverviewController();
        controller.articleTable = new TableView<>();
        ObservableList<ArticleModel> models = FXCollections.observableArrayList();
        models.addAll(new ModelFactory().createArticleModels(createDummyArticles(5)));
        when(mockMainControllerApp.getArticleList()).thenReturn(mockArticleList);
        when(mockArticleList.get()).thenReturn(models);

        controller.initialize(mockMainControllerApp);

        assertEquals(models, controller.articleTable.getItems());
    }

}