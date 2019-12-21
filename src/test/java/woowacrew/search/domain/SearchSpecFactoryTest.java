package woowacrew.search.domain;

import org.junit.jupiter.api.Test;
import woowacrew.search.exception.NotExistSearchTypeException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchSpecFactoryTest {
    @Test
    void 정상적으로_Article_제목으로_찾는_Specification을_생성한다() {
        SearchType searchType = SearchType.find("title");

        assertNotNull(SearchSpecFactory.createSpecification(searchType, "test"));
    }

    @Test
    void 정상적으로_Article_작성자로_찾는_Specification을_생성한다() {
        SearchType searchType = SearchType.find("author");

        assertNotNull(SearchSpecFactory.createSpecification(searchType, "test"));
    }

    @Test
    void 정상적으로_Article_제목_또는_내용로_찾는_Specification을_생성한다() {
        SearchType searchType = SearchType.find("titleWithContent");

        assertNotNull(SearchSpecFactory.createSpecification(searchType, "test"));
    }

    @Test
    void 정상적으로_타입을_생성한다() {
        assertDoesNotThrow(() -> {
            List<SearchType> searchTypes = Arrays.asList(SearchType.values());
            SearchType searchType = SearchSpecFactory.createSearchType(searchTypes, "title");
            assertNotNull(searchType);
        });
    }

    @Test
    void 지정한_타입에_없는_경우_예외가_발생한다() {
        assertThrows(NotExistSearchTypeException.class, () -> {
            List<SearchType> searchTypes = Arrays.asList(SearchType.TITLE_WITH_CONTENT, SearchType.AUTHOR);
            SearchSpecFactory.createSearchType(searchTypes, "title");
        });
    }

    @Test
    void 존재하지_않는_타입을_생성하는_경우_예외가_발생한() {
        assertThrows(NotExistSearchTypeException.class, () -> {
            SearchSpecFactory.createSearchType(Arrays.asList(SearchType.values()), "test");
        });
    }
}