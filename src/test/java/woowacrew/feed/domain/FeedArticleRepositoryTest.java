package woowacrew.feed.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FeedArticleRepositoryTest {
    @Autowired
    private FeedSourceRepository feedSourceRepository;

    @Autowired
    private FeedArticleRepository feedArticleRepository;

    @Test
    void 요청한_페이지_대로_findAll_하는지_테스트() {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishedDate");
        Pageable pageable = PageRequest.of(0, 20, sort);

        List<FeedArticle> articles = feedArticleRepository.findAll(pageable).getContent();
        assertThat(articles.size()).isEqualTo(6);
        assertThat(articles.get(0).getPublishedDate().toString()).contains("2019-10-06");
    }

    @Test
    void FeedSource로_검색() {
        FeedSource feedSource = new FeedSource("https://vsh123.github.io/feed.xml", "Description");
        FeedSource savedFeedArticle = feedSourceRepository.save(feedSource);
        FeedArticle feedArticle = new FeedArticle("title", "link", LocalDateTime.now(), savedFeedArticle);
        feedArticleRepository.save(feedArticle);

        List<FeedArticle> result = feedArticleRepository.findByFeedSource(feedSource);

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getTitle()).isEqualTo("title");
    }

    @Test
    void existsByLink() {
        String link = "link";
        FeedSource savedFeedSource = feedSourceRepository.save(new FeedSource("source", "description"));
        FeedArticle feedArticle = new FeedArticle("title", link, LocalDateTime.now(), savedFeedSource);
        feedArticleRepository.save(feedArticle);
        assertThat(feedArticleRepository.existsByLink(link)).isTrue();
    }
}