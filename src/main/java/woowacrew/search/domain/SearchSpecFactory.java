package woowacrew.search.domain;

import org.springframework.data.jpa.domain.Specification;
import woowacrew.search.exception.InvalidFieldPathException;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public class SearchSpecFactory {
    private static final String CONTENT_FORMAT = "%%%s%%";

    public static <T> Specification<T> createLikeSpecification(SearchType type, String content) {
        String[][] fieldPaths = type.getFieldPaths();
        String pattern = String.format(CONTENT_FORMAT, content);

        return createSpecificationBy(fieldPaths, pattern);
    }

    private static <T> Specification<T> createSpecificationBy(String[][] fieldPaths, String pattern) {
        if (fieldPaths.length == 0) {
            throw new InvalidFieldPathException();
        }
        Specification<T> specification = createLikeSpecBy(fieldPaths[0], pattern);
        for (int i = 1; i < fieldPaths.length; i++) {
            specification = specification.or(createLikeSpecBy(fieldPaths[i], pattern));
        }
        return specification;
    }

    private static <T> Specification<T> createLikeSpecBy(String[] fieldPath, String pattern) {
        return (Specification<T>) (root, query, builder) -> {
            Path<String> path = convertPath(root, fieldPath);
            return builder.like(path, pattern);
        };
    }

    public static <T> Specification<T> createMatchSpecification(SearchType type, Object content) {
        String[][] fieldPaths = type.getFieldPaths();

        return createMatchSpecBy(fieldPaths[0], content);
    }

    private static <T> Specification<T> createMatchSpecBy(String[] fieldPath, Object content) {
        return (Specification<T>) (root, query, builder) -> {
            Path<String> path = convertPath(root, fieldPath);
            return builder.equal(path, content);
        };
    }

    private static <T> Path<String> convertPath(Root<T> root, String[] fieldPath) {
        if (fieldPath.length == 0) {
            throw new InvalidFieldPathException();
        }
        Path<String> path = root.get(fieldPath[0]);
        for (int i = 1; i < fieldPath.length; i++) {
            path = path.get(fieldPath[i]);
        }
        return path;
    }
}
