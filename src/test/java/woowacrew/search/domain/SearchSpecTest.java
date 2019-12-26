package woowacrew.search.domain;

import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;
import woowacrew.article.free.domain.Article;
import woowacrew.search.exception.NotExistSearchTypeException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SearchSpecTest {
    @Test
    void 정상적으로_제목으로_찾는_Specification을_생성한다() {
        assertNotNull(createTestSearchSpec("title"));
    }

    @Test
    void 정상적으로_제목과_내용으로_찾는_Specification을_생성한다() {
        assertNotNull(createTestSearchSpec("titleWithContent"));
    }

    @Test
    void 정상적으로_작성자로_찾는_Specification을_생성한다() {
        assertNotNull(createTestSearchSpec("author"));
    }

    @Test
    void 유효하지_않는_검색_타입으로_생성하는_경우_예외가_발생한다() {
        assertThrows(NotExistSearchTypeException.class, () -> createTestSearchSpec("kkk"));
    }

    @Test
    void 지정하지_않은_타입을_생성하려는_경우_예외가_발생한다() {
        assertThrows(NotExistSearchTypeException.class, () -> {
            SearchType[] searchTypes = {SearchType.TITLE_WITH_CONTENT, SearchType.AUTHOR};
            SearchSpec<Article> searchSpec = SearchSpec.init("title", "test", searchTypes);
            searchSpec.getSpecification();
        });
    }

    private Specification<Article> createTestSearchSpec(String type) {
        SearchType[] searchTypes = {SearchType.TITLE, SearchType.TITLE_WITH_CONTENT, SearchType.AUTHOR};
        SearchSpec<Article> searchSpec = SearchSpec.init(type, "test", searchTypes);
        return searchSpec.getSpecification();
    }
}