package woowacrew.search.domain;

import org.junit.jupiter.api.Test;
import woowacrew.search.exception.NotExistSearchTypeException;

import static org.junit.jupiter.api.Assertions.*;

class AllowedSearchTypesTest {
    @Test
    void 정상적으로_타입을_생성한다() {
        assertDoesNotThrow(() -> {
            SearchType[] allowedSearchTypes = SearchType.values();
            AllowedSearchTypes searchTypeFactory = new AllowedSearchTypes(allowedSearchTypes);
            assertNotNull(searchTypeFactory.find("title"));
        });
    }

    @Test
    void 지정한_타입에_없는_경우_예외가_발생한다() {
        assertThrows(NotExistSearchTypeException.class, () -> {
            SearchType[] allowedSearchTypes = {SearchType.TITLE_WITH_CONTENT, SearchType.AUTHOR};
            AllowedSearchTypes searchTypeFactory = new AllowedSearchTypes(allowedSearchTypes);
            searchTypeFactory.find("title");
        });
    }

    @Test
    void 존재하지_않는_타입을_생성하는_경우_예외가_발생한() {
        assertThrows(NotExistSearchTypeException.class, () -> {
            AllowedSearchTypes allowedSearchTypes = new AllowedSearchTypes(SearchType.values());
            allowedSearchTypes.find("test");
        });
    }
}