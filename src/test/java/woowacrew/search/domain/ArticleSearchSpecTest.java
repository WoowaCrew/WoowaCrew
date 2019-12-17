package woowacrew.search.domain;

import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;
import woowacrew.article.free.domain.Article;
import woowacrew.search.exception.NotMatchArticleSearchKeyException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArticleSearchSpecTest {
    @Test
    void 정상적으로_ArticleSearchSpec을_생성한다() {
        Specification<Article> specification = ArticleSearchSpec.getSpecification("title", "test");

        assertNotNull(specification);
    }

    @Test
    void 올바르지_않은_타입으로_생성하려고하면_예외가_발생() {
        assertThrows(NotMatchArticleSearchKeyException.class, () -> ArticleSearchSpec.getSpecification("test title", "test"));
    }
}