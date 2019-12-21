package woowacrew.search.domain;

import woowacrew.search.exception.NotExistSearchTypeException;

import java.util.List;
import java.util.Optional;

public class SearchTypeFactory {
    public static SearchType createSearchType(List<SearchType> searchTypes, String type) {
        return Optional.ofNullable(SearchType.find(type))
                .filter(searchTypes::contains)
                .orElseThrow(NotExistSearchTypeException::new);
    }
}
