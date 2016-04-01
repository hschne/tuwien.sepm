package dao;

import entities.Article;

import java.util.List;

/**
 * Article interaction with a database
 */
public interface ArticleDao {
    /**
     * Create a new article
     *
     * @param article Article to save
     * @throws DaoException Thrown if there is a problem with the database
     */
    void create(Article article) throws DaoException;

    /**
     * Retrieve all visible articles
     *
     * @return A list of articles
     * @throws DaoException Thrown if there is a problem with the database
     */
    List<Article> getVisible() throws DaoException;

    /**
     * Updates an existing article
     *
     * @param article Article to update
     * @throws DaoException Thrown if there is a problem with the database
     */
    void update(Article article) throws DaoException;

    /**
     * Deletes an existing article
     *
     * @param article Article to delete
     * @throws DaoException Thrown if there is a problem with the database
     */
    void delete(Article article) throws DaoException;


}
