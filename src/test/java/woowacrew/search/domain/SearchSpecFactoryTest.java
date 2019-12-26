package woowacrew.search.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SearchSpecFactoryTest {
    @Test
    void 정상적으로_제목으로_찾는_Specification을_생성한다() {
        SearchType searchType = SearchType.find("title");

        assertNotNull(SearchSpecFactory.createLikeSpecification(searchType, "test"));
    }

    @Test
    void 정상적으로_작성자로_찾는_Specification을_생성한다() {
        SearchType searchType = SearchType.find("author");

        assertNotNull(SearchSpecFactory.createLikeSpecification(searchType, "test"));
    }

    @Test
    void 정상적으로_제목_또는_내용로_찾는_Specification을_생성한다() {
        SearchType searchType = SearchType.find("titleWithContent");

        assertNotNull(SearchSpecFactory.createLikeSpecification(searchType, "test"));
    }
}