package woowacrew.feed.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FeedArticleRepository extends JpaRepository<FeedArticle, Long> {
    Page<FeedArticle> findAll(Pageable pageable);
}
