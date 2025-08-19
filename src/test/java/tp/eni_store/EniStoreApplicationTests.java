package tp.eni_store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tp.eni_store.bo.Article;
import tp.eni_store.service.ArticleService;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class EniStoreApplicationTests {

    @Autowired
    ArticleService articleService;

	@Test
	void contextLoads() {
	}

    @Test
    void GetAll_Test() {
        assertThat(articleService.findAll().code).isEqualTo(202);
    }

    @Test
    void GetById_Test() {
        // Cas 1 - 703
        assertThat(articleService.findById(1586).code).isEqualTo(703);

        // Cas 2 - 202
        assertThat(articleService.findById(1).code).isEqualTo(202);
    }

    @Test
    void Delete_Test() {
        // Cas 1 - 703
        assertThat(articleService.delete(1586).code).isEqualTo(703);

        // Cas 2 - 202
        assertThat(articleService.delete(1).code).isEqualTo(202);
    }

    @Test
    void Save_Test() {
        // Cas 1 - 202 (Creation)
        Article newArticle = new Article(1596, "Titre test");
        assertThat(articleService.save(newArticle).code).isEqualTo(202);

        // Cas 2 - 203 (Article existant à modifier)
        Article foundArticle = articleService.findById(1).data;
        foundArticle.title = "Nouveau titre";

        assertThat(articleService.save(foundArticle).code).isEqualTo(203);
    }

}
