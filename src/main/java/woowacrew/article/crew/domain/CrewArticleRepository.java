package woowacrew.article.crew.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import woowacrew.degree.domain.Degree;

public interface CrewArticleRepository extends JpaRepository<CrewArticle, Long> {
    Page<CrewArticle> findAllByBasicArticleFormAuthorDegree(Degree degree, Pageable pageable);
}
