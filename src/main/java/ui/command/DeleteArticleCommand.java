package ui.command;

import entities.Article;
import ui.Main;

public class DeleteArticleCommand implements Command<Article> {


    private Main mainApp;
    private Article article;

    public DeleteArticleCommand(Main mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void execute() {
        mainApp.getArticleList().get().remove(article);
    }

    @Override
    public void setParameter(Article article) {
        this.article = article;
    }
}
