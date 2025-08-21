package tp.eni_store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tp.eni_store.bo.Article;
import tp.eni_store.service.ArticleService;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("mock")
@SpringBootTest
class EniStoreApplicationTests {

    @Autowired
    ArticleService articleService;

    @Test
    void GetAll_Test() {
        assertThat(articleService.findAll().code).isEqualTo(202);
    }

    @Test
    void GetById_Test() {
        // Cas 1 - 703
        assertThat(articleService.findById("1586").code).isEqualTo(703);

        // Cas 2 - 202
        assertThat(articleService.findById("1").code).isEqualTo(202);

        // Cas 2 - Mongo
        //assertThat(articleService.findById("68a5c5a3d23b3f2d27d8ff29").code).isEqualTo(202);
    }

    @Test
    void Delete_Test() {
        // Cas 1 - 703
        assertThat(articleService.delete("1586").code).isEqualTo(703);

        // Cas 2 - 202
        assertThat(articleService.delete("1").code).isEqualTo(202);

        // Cas 2 - Mongo
        //assertThat(articleService.delete("68a5c5a3d23b3f2d27d8ff29").code).isEqualTo(202);
    }

    @Test
    void Save_Test() {
        //Mock
        // Cas 1 - 202 (Creation)
        Article newArticle = new Article("1596", "Titre test");
        assertThat(articleService.save(newArticle).code).isEqualTo(202);

        // Cas 2 - 203 (Article existant à modifier)
        Article foundArticle = articleService.findById("1").data;
        foundArticle.title = "Nouveau titre";

        assertThat(articleService.save(foundArticle).code).isEqualTo(203);

        // Cas 1 - Création => doit renvoyer 202
        /*Article newArticle = new Article(null, "Titre test");
        assertThat(articleService.save(newArticle).code).isEqualTo(202);
// Cas 2 - Récupérer un article déjà en base (existant)
        Article foundArticle = articleService.findById("68a5c5a3d23b3f2d27d8ff29").data;
        foundArticle.title = "Nouveau titre";

// Doit renvoyer 203 car update
        assertThat(articleService.save(foundArticle).code).isEqualTo(203);*/

    }

}
