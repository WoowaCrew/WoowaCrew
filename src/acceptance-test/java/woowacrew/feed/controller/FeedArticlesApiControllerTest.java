package woowacrew.feed.controller;


import org.junit.jupiter.api.Test;
import woowacrew.common.controller.CommonTestController;
import woowacrew.feed.dto.FeedArticleResponseDtos;

import static org.assertj.core.api.Assertions.assertThat;

public class FeedArticlesApiControllerTest extends CommonTestController {

    @Test
    void findAllFeedArticles() {
        String cookie = loginWithCrew();
        FeedArticleResponseDtos result = webTestClient.get()
                .uri("/api/feed-articles?page=1")
                .header("Cookie", cookie)
                .exchange()
                .expectBody(FeedArticleResponseDtos.class)
                .returnResult()
                .getResponseBody();

        assertThat(result.getPageNumber()).isEqualTo(1);
        assertThat(result.getArticles().size()).isNotZero();
    }
}
