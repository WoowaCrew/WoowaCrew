package woowacrew.search.domain;

import org.junit.jupiter.api.Test;
import woowacrew.article.free.domain.Article;
import woowacrew.search.exception.NotExistSearchTypeException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SearchSpecTest {
    @Test
    void 정상적으로_Article_제목으로_찾는_Specification을_생성한다() {
        SearchSpec<Article> articleSearchSpec = new SearchSpec<>("title", "test");

        assertNotNull(articleSearchSpec.getSpecification());
    }

    @Test
    void 정상적으로_Article_작성자로_찾는_Specification을_생성한다() {
        SearchSpec<Article> articleSearchSpec = new SearchSpec<>("author", "test");

        assertNotNull(articleSearchSpec.getSpecification());
    }

    @Test
    void 정상적으로_Article_제목_또는_내용로_찾는_Specification을_생성한다() {
        SearchSpec<Article> articleSearchSpec = new SearchSpec<>("titleWithContent", "test");

        assertNotNull(articleSearchSpec.getSpecification());
    }

    @Test
    void 올바르지_않은_타입으로_생성하려고하면_예외가_발생() {
        assertThrows(NotExistSearchTypeException.class, () -> new SearchSpec<>("test test test", "test"));
    }
}