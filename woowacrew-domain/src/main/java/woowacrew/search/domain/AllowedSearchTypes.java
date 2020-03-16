package woowacrew.search.domain;

import woowacrew.search.exception.NotExistSearchTypeException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AllowedSearchTypes {

    private List<SearchType> allowedSearchTypes;

    public AllowedSearchTypes(SearchType[] allowedSearchTypes) {
        List<SearchType> convertSearchTypes = Arrays.asList(allowedSearchTypes);
        this.allowedSearchTypes = Collections.unmodifiableList(convertSearchTypes);
    }

    public SearchType find(String requestSearchType) {
        SearchType searchType = SearchType.find(requestSearchType);
        if (allowedSearchTypes.contains(searchType)) {
            return searchType;
        }
        throw new NotExistSearchTypeException();
    }
}
