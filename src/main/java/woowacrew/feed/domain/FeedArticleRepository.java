package woowacrew.feed.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FeedArticleRepository extends JpaRepository<FeedArticle, Long> {
    Page<FeedArticle> findAll(Pageable pageable);

    List<FeedArticle> findByFeedSource(FeedSource feedSource);
}
