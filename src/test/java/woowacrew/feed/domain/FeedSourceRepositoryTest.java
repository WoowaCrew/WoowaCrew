package woowacrew.feed.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class FeedSourceRepositoryTest {
    @Autowired
    private FeedSourceRepository feedSourceRepository;

    @Test
    void existsBySourceUrl() {
        FeedSource feedSource = new FeedSource("https://vsh123.github.io/feed.xml", "우테코 쉐이크반의 블로그");
        feedSourceRepository.save(feedSource);

        assertThat(feedSourceRepository.existsBySourceUrl(feedSource.getSourceUrl())).isTrue();
    }

    @Test
    void null값_체크() {
        FeedSource feedSource = new FeedSource(null, null);

        assertThrows(DataIntegrityViolationException.class,() ->feedSourceRepository.save(feedSource));
    }
}