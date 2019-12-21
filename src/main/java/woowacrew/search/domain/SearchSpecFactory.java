package woowacrew.search.domain;

import org.springframework.data.jpa.domain.Specification;
import woowacrew.search.exception.InvalidFieldPathException;
import woowacrew.search.exception.NotExistSearchTypeException;

import javax.persistence.criteria.Path;
import java.util.List;
import java.util.Optional;

public class SearchSpecFactory {
    private static final String PREFIX_REGEX = "%";
    private static final String SUBFIX_REGEX = "%";
    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 2;

    public static SearchType createSearchType(List<SearchType> searchTypes, String type) {
        return Optional.ofNullable(SearchType.find(type))
                .filter(searchTypes::contains)
                .orElseThrow(NotExistSearchTypeException::new);
    }

    public static <T> Specification<T> createSpecification(SearchType type, String content) {
        String[][] fieldPaths = type.getFieldPaths();
        String pattern = PREFIX_REGEX + content + SUBFIX_REGEX;

        return createSpecBy(fieldPaths, pattern);
    }

    private static <T> Specification<T> createSpecBy(String[][] fieldPaths, String pattern) {
        if (fieldPaths.length == MAX_SIZE) {
            return createSpecBy(fieldPaths[0], fieldPaths[1], pattern);
        }
        if (fieldPaths.length == MIN_SIZE) {
            return createSpecBy(fieldPaths[0], pattern);
        }
        throw new InvalidFieldPathException();
    }

    private static <T> Specification<T> createSpecBy(String[] firstFieldPath, String[] secondFieldPath, String pattern) {
        Specification<T> firstSpecification = createSpecBy(firstFieldPath, pattern);
        Specification<T> secondSpecification = createSpecBy(secondFieldPath, pattern);

        return firstSpecification.or(secondSpecification);
    }

    private static <T> Specification<T> createSpecBy(String[] fieldPath, String pattern) {
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
}
