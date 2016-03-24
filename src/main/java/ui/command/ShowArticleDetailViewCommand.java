package ui.command;

import ui.Main;
import ui.model.ArticleModel;

public class ShowArticleDetailViewCommand implements Command {

    private Main mainApp;
    private ArticleModel article;

    public ShowArticleDetailViewCommand(Main mainApp, ArticleModel article) {
        this.mainApp = mainApp;
        this.article = article;
    }

    @Override
    public void execute() {
        mainApp.showArticleDetails(article);
    }
}
