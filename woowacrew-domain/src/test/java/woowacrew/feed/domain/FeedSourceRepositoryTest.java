package woowacrew.feed.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import woowacrew.JpaTestConfiguration;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = JpaTestConfiguration.class)
class FeedSourceRepositoryTest {
    @Autowired
    private FeedSourceRepository feedSourceRepository;
    @Autowired
    private FeedArticleRepository feedArticleRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void existsBySourceUrl() {
        FeedSource feedSource = new FeedSource("https://vsh123.github.io/feed.xml", "우테코 쉐이크반의 블로그");
        feedSourceRepository.save(feedSource);

        assertThat(feedSourceRepository.existsBySourceUrl(feedSource.getSourceUrl())).isTrue();
    }

    @Test
    void null값_체크() {
        FeedSource feedSource = new FeedSource(null, null);

        assertThrows(DataIntegrityViolationException.class, () -> feedSourceRepository.save(feedSource));
    }

    @Test
    void delete_테스트() throws IOException {
        String sourceUrl = new ClassPathResource("feed.xml").getURL().toString();
        FeedSource feedSource = new FeedSource(sourceUrl, "description");

        FeedSource savedFeedSource = feedSourceRepository.save(feedSource);
        FeedArticles feedArticles = savedFeedSource.createFeedArticles();
        feedArticleRepository.saveAll(feedArticles.getFeedArticles());

        feedSourceRepository.deleteById(savedFeedSource.getId());
        feedSourceRepository.flush();

        List<FeedArticle> actualFeedArticles = feedArticleRepository.findByFeedSource(savedFeedSource);

        assertThat(actualFeedArticles.size()).isZero();
    }
}