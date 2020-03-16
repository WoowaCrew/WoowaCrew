package woowacrew.search.domain;

import org.springframework.data.jpa.domain.Specification;

public class SearchSpec<T> {

    private Specification<T> specification;

    private SearchSpec() {
    }

    public static <T> SearchSpec<T> init(String requestType, String requestContent, SearchType[] requestAllowedTypes) {
        AllowedSearchTypes allowedSearchTypes = new AllowedSearchTypes(requestAllowedTypes);
        SearchType searchType = allowedSearchTypes.find(requestType);

        SearchSpec<T> searchSpec = new SearchSpec<>();
        searchSpec.specification = SearchSpecFactory.createLikeSpecification(searchType, requestContent);
        return searchSpec;
    }

    public Specification<T> getSpecification() {
        return specification;
    }

    public Specification<T> and(String requestType, Object requestContent) {
        SearchType searchType = SearchType.find(requestType);
        Specification<T> specification = SearchSpecFactory.createMatchSpecification(searchType, requestContent);

        return this.specification.and(specification);
    }
}
