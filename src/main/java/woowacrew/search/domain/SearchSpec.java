package woowacrew.search.domain;

import org.springframework.data.jpa.domain.Specification;

public interface SearchSpec {
    Specification<?> getSpecification();
}
