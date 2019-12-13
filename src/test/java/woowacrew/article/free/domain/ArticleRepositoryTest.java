package woowacrew.article.free.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void Id와_시간이_정상적으로_반환되는지_확인() {
        Article article = new Article("title", "content", null);
        Article actualArticle = articleRepository.save(article);

        assertThat(actualArticle.getId()).isNotNull();
        assertThat(actualArticle.getCreatedDate()).isNotNull();
    }
}