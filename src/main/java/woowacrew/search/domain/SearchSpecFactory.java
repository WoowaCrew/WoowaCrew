package woowacrew.search.domain;

import org.springframework.data.jpa.domain.Specification;
import woowacrew.search.exception.InvalidFieldPathException;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class SearchSpecFactory {
    private static final String CONTENT_FORMAT = "%%%s%%";

    private SearchSpecFactory() {
    }

    public static <T> Specification<T> createLikeSpecification(SearchType requestType, String requestContent) {
        List<FieldPath> fieldPaths = requestType.getFieldPaths();
        String pattern = String.format(CONTENT_FORMAT, requestContent);

        return createSpecificationBy(fieldPaths, pattern);
    }

    private static <T> Specification<T> createSpecificationBy(List<FieldPath> fieldPaths, String pattern) {
        if (fieldPaths.size() == 0) {
            throw new InvalidFieldPathException();
        }

        Specification<T> specification = Specification.where(null);
        for (FieldPath fieldPath : fieldPaths) {
            specification = specification.or(createLikeSpecBy(fieldPath, pattern));
        }
        return specification;
    }

    private static <T> Specification<T> createLikeSpecBy(FieldPath fieldPath, String pattern) {
        return (Specification<T>) (root, query, builder) -> {
            Path<String> path = convertPath(root, fieldPath);
            return builder.like(path, pattern);
        };
    }

    public static <T> Specification<T> createMatchSpecification(SearchType type, Object content) {
        List<FieldPath> fieldPaths = type.getFieldPaths();

        return createMatchSpecBy(fieldPaths.get(0), content);
    }

    private static <T> Specification<T> createMatchSpecBy(FieldPath fieldPath, Object content) {
        return (Specification<T>) (root, query, builder) -> {
            Path<String> path = convertPath(root, fieldPath);
            return builder.equal(path, content);
        };
    }

    private static <T> Path<String> convertPath(Root<T> root, FieldPath fieldPath) {
        Path<String> path = root.get(fieldPath.getRootPath());
        List<String> subPaths = fieldPath.getSubPaths();
        for (String subPath : subPaths) {
            path = path.get(subPath);
        }
        return path;
    }
}
