package tp.eni_store.api;

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

    @GetMapping("all")
    public ServiceResponse<List<Article>> apiSelectAllArticle() {
        return articleService.findAll();
    }

    @GetMapping("get/{id}")
    public ServiceResponse<Article> apiSelectArticleById(@PathVariable String id) {
        int idInt = Integer.parseInt(id);
        return articleService.findById(idInt);
    }

    //@GetMapping("api/articles/delete/{id}")

    @DeleteMapping("/{id}")
    public ServiceResponse<Article> apiDeleteArticleById(@PathVariable String id) {
        int idInt = Integer.parseInt(id);
        return articleService.delete(idInt);
    }

    //@GetMapping("api/articles/create/{id}")
    @PostMapping("create")
    public ServiceResponse<Article> apiCreateArticleById(@RequestBody Article article) {
        //Article article = new Article();
        /*article.id = id;
        article.title = "Nouvelle article " + id;
        ServiceResponse<Article> serviceResponse = articleService.save(article);*/
        return articleService.save(article);
    }
}
