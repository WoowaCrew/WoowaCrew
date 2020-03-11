package woowacrew.article.anonymous.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnonymousArticleRepository extends JpaRepository<AnonymousArticle, Long> {
    Page<AnonymousArticle> findAll(Specification<AnonymousArticle> specification, Pageable pageable);

    Page<AnonymousArticle> findByIsApproved(boolean isApproved, Pageable pageable);
    List<AnonymousArticle> findByIsApproved(boolean isApproved);
}
