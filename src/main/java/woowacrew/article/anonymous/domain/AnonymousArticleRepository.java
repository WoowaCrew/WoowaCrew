package woowacrew.article.anonymous.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnonymousArticleRepository extends JpaRepository<AnonymousArticle, Long> {
    List<AnonymousArticle> findByIsApproved(boolean isApproved);
}
