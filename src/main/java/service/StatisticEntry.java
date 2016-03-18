package service;

import entities.Article;

public class StatisticEntry {


    private Article article;
    private int totalTimesSold;

    public StatisticEntry(Article article, int totalTimesSold) {
        this.article = article;
        this.totalTimesSold = totalTimesSold;
    }

    public int getTotalTimesSold() {
        return totalTimesSold;
    }

    public Article getArticle() {
        return article;
    }


}
