package woowacrew.search.domain;

import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchSpec<T> {

    private List<SearchType> allowedSearchTypes;

    public SearchSpec(SearchType... allowedSearchTypes) {
        List<SearchType> convertSearchTypes = Arrays.asList(allowedSearchTypes);
        this.allowedSearchTypes = Collections.unmodifiableList(convertSearchTypes);
    }

    public Specification<T> getSpecification(String type, String content) {
        SearchType searchType = SearchTypeFactory.createSearchType(this.allowedSearchTypes, type);
        return SearchSpecFactory.createSpecification(searchType, content);
    }
}
