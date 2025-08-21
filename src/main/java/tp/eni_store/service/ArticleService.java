package tp.eni_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import tp.eni_store.bo.Article;
import tp.eni_store.dao.IDAOArticle;
import tp.eni_store.dao.mock.DAOArticleMock;
import tp.eni_store.dao.DAOSaveResult;
import tp.eni_store.locale.LocaleHelper;

import java.util.List;

@Service
public class ArticleService {

    private final IDAOArticle daoArticle;
    final MessageSource messageSource;
    final LocaleHelper localeHelper;

    public ArticleService(IDAOArticle daoArticle, MessageSource messageSource, LocaleHelper localeHelper) {
        this.daoArticle = daoArticle;
        this.messageSource = messageSource;
        this.localeHelper = localeHelper;
    }


    public ServiceResponse<List<Article>> findAll() {
        /*ServiceResponse<List<Article>> serviceResponse = new ServiceResponse<>();
        serviceResponse.data = daoArticle.getAll();
        serviceResponse.code = 202;
        return serviceResponse;*/
        return ServiceHelper.buildResponse(202, daoArticle.getAll(), localeHelper.i18n("findAllArticle_202_Success"));
    }

    public ServiceResponse<Article> findById(String id) {
        Article article = daoArticle.getById(id);

        // Cas : Je trouve pas l'id
        if (article == null){
            return ServiceHelper.buildResponse(703, null, localeHelper.i18n("findArticle_703_NotFound"));
        }

        // Cas : Ok
        return ServiceHelper.buildResponse(202, article, localeHelper.i18n("findArticle_202_Success"));
    }

    public ServiceResponse delete(String id) {
        boolean removeSuccess = daoArticle.delete(id);

        ServiceResponse serviceResponse = new ServiceResponse();
        if (!removeSuccess) {
            return ServiceHelper.buildResponse(703, null, localeHelper.i18n("deleteArticle_703_NotFound"));
        }
        serviceResponse.code = 202;
        return ServiceHelper.buildResponse(202, null, localeHelper.i18n("deleteArticle_202_Success"));
    }

    public ServiceResponse<Article> save(Article article) {
        DAOSaveResult<Article> daoSaveResult = daoArticle.save(article);

        if (daoSaveResult.isCreated){
            return ServiceHelper.buildResponse(202, daoSaveResult.data, localeHelper.i18n("saveArticle_203_Success"));
        }

        return ServiceHelper.buildResponse(203, daoSaveResult.data, localeHelper.i18n("saveArticle_202_Success"));
    }
}
