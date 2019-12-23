package woowacrew.feed.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import woowacrew.feed.domain.FeedArticle;
import woowacrew.feed.domain.FeedSource;
import woowacrew.feed.dto.FeedSourceRequestDto;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FeedInternalServiceIntegrationTest {
    @Autowired
    private FeedInternalService feedInternalService;

    @Test
    void delete_테스트() throws IOException {
        String sourceUrl = new ClassPathResource("feed.xml").getURL().toString();
        FeedSourceRequestDto requestDto = new FeedSourceRequestDto(sourceUrl, "description");
        FeedSource feedSource = feedInternalService.registerFeedSource(requestDto);
        Pageable pageable = PageRequest.of(0, 20);
        Page<FeedArticle> feedArticles = feedInternalService.findAllFeedArticles(pageable);

        assertThat(feedArticles.getContent().size()).isNotZero();

        feedInternalService.deleteFeedSource(feedSource.getId());

        Page<FeedArticle> articles = feedInternalService.findAllFeedArticles(pageable);

        assertThat(feedArticles.getTotalElements()).isGreaterThan(articles.getTotalElements());
    }
}