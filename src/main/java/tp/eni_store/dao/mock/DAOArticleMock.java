package tp.eni_store.dao.mock;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tp.eni_store.bo.Article;
import tp.eni_store.dao.DAOSaveResult;
import tp.eni_store.dao.IDAOArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

//injecter
@Profile("mock")
@Component
public class DAOArticleMock implements IDAOArticle {
    List<Article> articles;

    public DAOArticleMock() {
        //Liste vide
        articles = new ArrayList<>();

        //Génere 10 personnes
        for (int i =1; i<=10;i++) {
            String iString = String.valueOf(i);
            Article a = new Article(iString, "Article " + i);
            articles.add(a);
        }
    }

    public List<Article> getAll(){
        return articles;
    }

    public Article getById(String id){
        Optional<Article> foundArticle = articles.stream().filter(article -> Objects.equals(article.id, id)).findFirst();

        //retourne l'article trouvé ou null si il ne trouve pas
        return foundArticle.orElse(null);
    }

    public boolean delete(String id){
        return articles.removeIf(article -> Objects.equals(article.id, id));
    }

    public DAOSaveResult<Article> save(Article article){
        //si article existe en base alors update
        DAOSaveResult<Article> result = new DAOSaveResult<>();

        // sinon mettre à jour
        // -- Si article existe ne base alors le modifier
        Optional<Article> foundArticle = articles.stream().filter(value -> value.id == article.id).findFirst();

        // si existe -> alors je le modifie
        if (foundArticle.isPresent()){
            foundArticle.get().title = article.title;

            result.isCreated = false;
            result.data = foundArticle.get();

            return result;
        }

        // Si id == null
        // générer un faux id
        article.id = String.valueOf(articles.size() + 100);
        // Je ajoute l'article dans le tableau
        articles.add(article);

        result.isCreated = true;
        result.data = article;

        return result;

    }
}
