package woowacrew.article.anonymous.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonymousArticleRepository extends JpaRepository<AnonymousArticle, Long> {
    Page<AnonymousArticle> findByIsApproved(boolean isApproved, Pageable pageable);
}
