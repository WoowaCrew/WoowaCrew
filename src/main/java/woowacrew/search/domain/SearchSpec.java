package woowacrew.search.domain;

import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchSpec {
    public static <T> Specification<T> createSpecification(String type, String content, SearchType... searchTypes) {
        List<SearchType> convertSearchTypes = Arrays.asList(searchTypes);
        convertSearchTypes = Collections.unmodifiableList(convertSearchTypes);

        SearchType searchType = SearchTypeFactory.createSearchType(convertSearchTypes, type);
        return SearchSpecFactory.createSpecification(searchType, content);
    }
}
