package tp.eni_store.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tp.eni_store.bo.Article;
import tp.eni_store.service.ArticleService;
import tp.eni_store.service.ServiceResponse;

import java.util.List;

@RestController
@RequestMapping("api/articles/")
public class ArticleRestController {
    @Autowired
    ArticleService articleService;
/*
    @GetMapping("api/start")
    String hterzfe() {
        return "EniStore works";
    }*/

    @Operation(summary = "Récupérer tous les articles")
    @GetMapping("all")
    public ServiceResponse<List<Article>> apiSelectAllArticle() {
        return articleService.findAll();
    }

    @Operation(summary = "Récupérer un article par son ID")
    @GetMapping("get/{id}")
    public ServiceResponse<Article> apiSelectArticleById(@PathVariable String id) {

        return articleService.findById(id);
    }

    //@GetMapping("api/articles/delete/{id}")

    @Operation(summary = "Supprime un article par son ID")
    @DeleteMapping("/{id}")
    public ServiceResponse<Article> apiDeleteArticleById(@PathVariable String id) {

        return articleService.delete(id);
    }

    //@GetMapping("api/articles/create/{id}")
    @Operation(summary = "Enregistre un article, si l'id existe alors c'est une modification, sinon c'est une création")
    @PostMapping("create")
    public ServiceResponse<Article> apiCreateArticleById(@RequestBody Article article) {
        //Article article = new Article();
        /*article.id = id;
        article.title = "Nouvelle article " + id;
        ServiceResponse<Article> serviceResponse = articleService.save(article);*/
        return articleService.save(article);
    }
}
