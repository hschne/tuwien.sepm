package ui.controller.article;

import entities.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import service.ServiceException;
import service.criteria.DateOperator;
import service.decorator.ArticleSale;
import service.decorator.ArticleSaleFactory;
import service.decorator.SaleFactory;
import service.decorator.TimedArticleSaleFactory;
import ui.controller.AbstractController;
import ui.model.ArticleModel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleStatisticController extends AbstractController {


    @FXML
    public DatePicker datePicker;

    @FXML
    public BarChart<String, Integer> barChart;

    @FXML
    public CategoryAxis xAxis;

    private Stage dialogStage;

    private List<ArticleModel> articles;

    private final EventHandler<ActionEvent> dateInputListener = event -> setArticleSalesFromDate();

    public void initializeWith(Stage dialogStage, List<ArticleModel> articles) {
        this.articles = articles;
        datePicker.setOnAction(dateInputListener);
        initialzeChartLabels(dialogStage);
        setArticleSales();
    }

    @FXML
    public void handleClose() {
        dialogStage.close();
    }

    @FXML
    public void handleReset() {
        datePicker.getEditor().clear();
        setArticleSales();
    }

    private void initialzeChartLabels(Stage dialogStage) {
        this.dialogStage = dialogStage;
        ObservableList<String> xLabels = FXCollections.observableArrayList();
        xLabels.addAll(articles.stream().map(Article::getName).collect(Collectors.toList()));
        xAxis.setCategories(xLabels);
    }

    private void setArticleSales() {
        SaleFactory saleFactory = new ArticleSaleFactory(mainApp.getReceiptRepository());
        fillBarChart(saleFactory);
    }

    private void setArticleSalesFromDate() {
        LocalDate localDate = datePicker.getValue();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        SaleFactory saleFactory = new TimedArticleSaleFactory(mainApp.getReceiptRepository(), date, DateOperator.AFTER);
        fillBarChart(saleFactory);
    }

    private void fillBarChart(SaleFactory saleFactory) {
        barChart.getData().clear();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for (Article article : articles) {
            ArticleSale sale = saleFactory.create(article);
            int timesSold = tryGetTimesSold(sale);
            series.getData().add(new XYChart.Data<>(article.getName(), timesSold));
        }
        barChart.getData().add(series);
    }

    private int tryGetTimesSold(ArticleSale sale) {
        int timesSold = 0;
        try {
            timesSold = sale.getTimesSold();
        } catch (ServiceException e) {
            mainApp.getOutput().showExceptionNotification("Error",
                    "Could not retrieve times sold", "Please view logs for more details", e);
        }
        return timesSold;
    }


}
