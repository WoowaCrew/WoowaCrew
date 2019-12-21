package woowacrew.search.domain;

import org.junit.jupiter.api.Test;
import woowacrew.search.exception.NotExistSearchTypeException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArticleSearchSpecTest {
    @Test
    void 정상적으로_제목으로_찾는_Specification을_생성한다() {
        ArticleSearchSpec articleSearchSpec = new ArticleSearchSpec("title", "test");
        assertNotNull(articleSearchSpec.getSpecification());
    }

    @Test
    void 정상적으로_제목과_내용으로_찾는_Specification을_생성한다() {
        ArticleSearchSpec articleSearchSpec = new ArticleSearchSpec("titleWithContent", "test");
        assertNotNull(articleSearchSpec.getSpecification());
    }

    @Test
    void 정상적으로_작성자로_찾는_Specification을_생성한다() {
        ArticleSearchSpec articleSearchSpec = new ArticleSearchSpec("author", "test");
        assertNotNull(articleSearchSpec.getSpecification());
    }

    @Test
    void 유효하지_않는_검색_타입으로_생성하는_경우_예외가_발생한다() {
        assertThrows(NotExistSearchTypeException.class, () -> new ArticleSearchSpec("kkk", "test"));
    }
}