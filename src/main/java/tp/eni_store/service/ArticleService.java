package tp.eni_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.eni_store.bo.Article;
import tp.eni_store.dao.DAOArticle;
import tp.eni_store.dao.DAOSaveResult;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private DAOArticle daoArticle;

    public ServiceResponse<List<Article>> findAll() {
        /*ServiceResponse<List<Article>> serviceResponse = new ServiceResponse<>();
        serviceResponse.data = daoArticle.getAll();
        serviceResponse.code = 202;
        return serviceResponse;*/
        return ServiceHelper.buildResponse(202, daoArticle.getAll());
    }

    public ServiceResponse<Article> findById(int id) {
        Article article = daoArticle.getById(id);

        // Cas : Je trouve pas l'id
        if (article == null){
            return ServiceHelper.buildResponse(703);
        }

        // Cas : Ok
        return ServiceHelper.buildResponse(202, article);
    }

    public ServiceResponse delete(int id) {
        boolean removeSuccess = daoArticle.delete(id);

        ServiceResponse serviceResponse = new ServiceResponse();
        if (!removeSuccess) {
            return ServiceHelper.buildResponse(703);
        }
        serviceResponse.code = 202;
        return ServiceHelper.buildResponse(202);
    }

    public ServiceResponse<Article> save(Article article) {
        DAOSaveResult<Article> daoSaveResult = daoArticle.save(article);

        if (daoSaveResult.isCreated){
            return ServiceHelper.buildResponse(202, daoSaveResult.data);
        }

        return ServiceHelper.buildResponse(203, daoSaveResult.data);
    }
}
