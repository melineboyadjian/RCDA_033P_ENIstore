package tp.eni_store.dao;

import tp.eni_store.bo.Article;

import java.util.List;

public interface IDAOArticle {

    List<Article> getAll();

    Article getById(String id);

    boolean delete(String id);

    DAOSaveResult<Article> save(Article article);
}
