package tp.eni_store.dao.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tp.eni_store.bo.Article;
import tp.eni_store.dao.DAOSaveResult;
import tp.eni_store.dao.IDAOArticle;

import java.util.List;
import java.util.Optional;

@Profile("mongo")
@Component
public class DAOArticleMongo implements IDAOArticle {
    private final ArticleMongoRepository articleMongoRepository;

    public DAOArticleMongo(ArticleMongoRepository articleMongoRepository) {
        this.articleMongoRepository = articleMongoRepository;
    }

    @Override
    public List<Article> getAll() {
        return articleMongoRepository.findAll();
    }

    @Override
    public Article getById(String id) {
        return articleMongoRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(String id) {
        Optional<Article> article = articleMongoRepository.findById(id);

        // Si existe pas => false
        if (article.isEmpty()){
            return false;
        }

        // Sinon supprimer l'article et true
        articleMongoRepository.delete(article.get());

        return true;
    }

    @Override
    public DAOSaveResult<Article> save(Article article) {
        Article foundArticle = null;
        DAOSaveResult<Article> result = new DAOSaveResult<>();

        if(article.id != null) {
            foundArticle = articleMongoRepository.findById(article.id).orElse(null);
        }
        //si article existant
        if (foundArticle != null) {
            //execute la requete save
            article = articleMongoRepository.save(article);
            result.isCreated = false;
            result.data = article;
            return result;
        }
        article = articleMongoRepository.save(article);
        result.isCreated = true;
        result.data = article;
        return null;
    }

}
