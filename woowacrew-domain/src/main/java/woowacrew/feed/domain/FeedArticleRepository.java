package woowacrew.feed.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface FeedArticleRepository extends JpaRepository<FeedArticle, Long>, JpaSpecificationExecutor<FeedArticle> {
    Page<FeedArticle> findAll(Pageable pageable);

    Page<FeedArticle> findAll(Specification<FeedArticle> specification, Pageable pageable);

    List<FeedArticle> findByFeedSource(FeedSource feedSource);
}
