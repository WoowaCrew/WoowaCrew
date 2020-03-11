package woowacrew.article.crew.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import woowacrew.degree.domain.Degree;

public interface CrewArticleRepository extends JpaRepository<CrewArticle, Long>, JpaSpecificationExecutor<CrewArticle> {
    Page<CrewArticle> findAllByBasicArticleFormAuthorDegree(Degree degree, Pageable pageable);

    Page<CrewArticle> findAll(Specification<CrewArticle> specification, Pageable pageable);
}
