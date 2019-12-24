package woowacrew.search.domain;

import woowacrew.search.exception.NotExistSearchTypeException;

import java.util.List;
import java.util.Optional;

public class SearchTypeFactory {
    private SearchTypeFactory() {
    }

    public static SearchType createSearchType(List<SearchType> allowedSearchTypes, String type) {
        return Optional.ofNullable(SearchType.find(type))
                .filter(allowedSearchTypes::contains)
                .orElseThrow(NotExistSearchTypeException::new);
    }
}
