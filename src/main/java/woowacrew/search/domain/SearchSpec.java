package woowacrew.search.domain;

import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchSpec<T> {

    private Specification<T> specification;

    public SearchSpec(String type, String content, SearchType... allowedSearchTypes) {
        List<SearchType> convertSearchTypes = Arrays.asList(allowedSearchTypes);
        convertSearchTypes = Collections.unmodifiableList(convertSearchTypes);

        SearchType searchType = SearchTypeFactory.createSearchType(convertSearchTypes, type);
        this.specification = SearchSpecFactory.createLikeSpecification(searchType, content);
    }

    public Specification<T> getSpecification() {
        return specification;
    }

    public Specification<T> and(String type, Object content) {
        SearchType searchType = SearchType.find(type);
        Specification<T> specification = SearchSpecFactory.createMatchSpecification(searchType, content);

        return this.specification.and(specification);
    }
}
