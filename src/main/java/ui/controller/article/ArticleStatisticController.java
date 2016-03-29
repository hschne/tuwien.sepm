package ui.controller.article;

import entities.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import service.ServiceException;
import service.decorator.ArticleSale;
import service.decorator.ArticleSaleFactory;
import service.decorator.SaleFactory;
import ui.controller.AbstractController;
import ui.model.ArticleModel;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleStatisticController extends AbstractController{

    @FXML
    public DatePicker datePicker;

    @FXML
    public BarChart<String, Integer> barChart;

    @FXML
    public CategoryAxis xAxis;

    private Stage dialogStage;

    private ObservableList<String> articles;

    public void initializeWith(Stage dialogStage, List<ArticleModel> articles) {
        this.dialogStage = dialogStage;
        this.articles = FXCollections.observableArrayList();
        this.articles.addAll(articles.stream().map(ArticleModel::getName).collect(Collectors.toList()));
        xAxis.setCategories(this.articles);
        try {
            setArticleSales(articles);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleClose(){
        dialogStage.close();
    }

    private void setArticleSales(List<ArticleModel> articles) throws ServiceException {
        SaleFactory saleFactory = new ArticleSaleFactory(mainApp.getReceiptRepository());
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for(Article article : articles){
            ArticleSale sale = saleFactory.create(article);
            int timesSold = sale.getTimesSold();
            series.getData().add(new XYChart.Data<>(article.getName(), timesSold));
        }
        barChart.getData().add(series);
    }




}
