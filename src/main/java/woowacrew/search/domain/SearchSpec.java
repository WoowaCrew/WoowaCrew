package woowacrew.search.domain;

import org.springframework.data.jpa.domain.Specification;
import woowacrew.search.exception.InvalidFieldPathException;

import javax.persistence.criteria.Path;

public class SearchSpec<T> {
    private static final String PREFIX_REGEX = "%";
    private static final String SUBFIX_REGEX = "%";
    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 2;

    private Specification<T> specification;

    public SearchSpec(String type, String content) {
        SearchType searchType = SearchType.find(type);
        String[][] fieldPaths = searchType.getFieldPaths();
        String pattern = PREFIX_REGEX + content + SUBFIX_REGEX;

        this.specification = createSpecBy(fieldPaths, pattern);
    }

    private Specification<T> createSpecBy(String[][] fieldPaths, String pattern) {
        if (fieldPaths.length == MAX_SIZE) {
            return createSpecBy(fieldPaths[0], fieldPaths[1], pattern);
        }
        if (fieldPaths.length == MIN_SIZE) {
            return createSpecBy(fieldPaths[0], pattern);
        }
        throw new InvalidFieldPathException();
    }

    private Specification<T> createSpecBy(String[] firstFieldPath, String[] secondFieldPath, String pattern) {
        return createSpecBy(firstFieldPath, pattern).or(createSpecBy(secondFieldPath, pattern));
    }

    private Specification<T> createSpecBy(String[] fieldPath, String pattern) {
        return (Specification<T>) (root, query, builder) -> {
            if (fieldPath.length == 0) {
                throw new InvalidFieldPathException();
            }
            Path<String> path = root.get(fieldPath[0]);
            for (int i = 1; i < fieldPath.length; i++) {
                path = path.get(fieldPath[i]);
            }
            return builder.like(path, pattern);
        };
    }

    public Specification<T> getSpecification() {
        return specification;
    }
}
