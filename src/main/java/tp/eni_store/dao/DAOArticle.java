package tp.eni_store.dao;

import org.springframework.stereotype.Component;
import tp.eni_store.bo.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//injecter
@Component
public class DAOArticle {
    List<Article> articles;

    public DAOArticle() {
        //Liste vide
        articles = new ArrayList<>();

        //Génere 10 personnes
        for (int i =1; i<=10;i++) {
            Article a = new Article(i, "Article " + i);
            articles.add(a);
        }
    }

    public List<Article> getAll(){
        return articles;
    }

    public Article getById(int id){
        Optional<Article> foundArticle = articles.stream().filter(article -> article.id == id).findFirst();

        //retourne l'article trouvé ou null si il ne trouve pas
        return foundArticle.orElse(null);
    }

    public boolean delete(int id){
        return articles.removeIf(article -> article.id == id);
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
        article.id = articles.size() + 100;
        // Je ajoute l'article dans le tableau
        articles.add(article);

        result.isCreated = true;
        result.data = article;

        return result;

    }
}
