package ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.junit.Test;
import org.mockito.Mock;
import ui.FXTest;
import ui.MainApp;
import ui.controller.article.ArticleOverviewController;
import ui.model.ArticleList;
import ui.model.ArticleModel;
import ui.model.ModelFactory;

import static base.DummyEntityFactory.createDummyArticles;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ArticleOverviewControllerTest extends FXTest {


    @Mock
    MainApp mockMainApp;
    @Mock
    ArticleList mockArticleList;

    @Test
    public void initialize_InitializeWithMainApp_ArticleTableFilled() throws Exception {
        ArticleOverviewController controller = new ArticleOverviewController();
        controller.articleTable = new TableView<>();
        ObservableList<ArticleModel> models = FXCollections.observableArrayList();
        models.addAll(new ModelFactory().createArticleModels(createDummyArticles(5)));
        when(mockMainApp.getArticleList()).thenReturn(mockArticleList);
        when(mockArticleList.get()).thenReturn(models);

        controller.initialize(mockMainApp);

        assertEquals(models, controller.articleTable.getItems());
    }

    @Test
    public void handleCreate_CreateArticle_MainAppShowDetailViewCalled() throws Exception {
        ArticleOverviewController controller = new ArticleOverviewController();
        controller.articleTable = new TableView<>();
        controller.nameColumn = new TableColumn<>();
        controller.descriptionColumn= new TableColumn<>();
        controller.priceColumn = new TableColumn<>();
        controller.categoryColumn = new TableColumn<>();
        when(mockMainApp.getArticleList()).thenReturn(mockArticleList);

        controller.initialize();
        controller.initialize(mockMainApp);
        controller.handleCreate();

        verify(mockMainApp).showArticleDetails(null);
    }

}