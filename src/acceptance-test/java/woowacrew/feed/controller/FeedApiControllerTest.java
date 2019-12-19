package woowacrew.feed.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import woowacrew.common.controller.CommonTestController;
import woowacrew.feed.dto.FeedArticleResponseDtos;
import woowacrew.feed.dto.FeedSourceDto;

import static org.assertj.core.api.Assertions.assertThat;

public class FeedApiControllerTest extends CommonTestController {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void FeedSource_등록시_관리자가_아니라면_accessdeny로_리다이렉션() {
        String url = "https://oeeen.github.io/feed.xml";
        String description = "테스트";
        String cookie = loginWithCrew();
        webTestClient.post()
                .uri("/api/feeds")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("sourceUrl", url)
                        .with("description", description)).exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/accessdeny"));
    }

    @Test
    void feedSourceRegisterTest() {
        String url = "https://oeeen.github.io/feed.xml";
        String description = "테스트";
        String cookie = loginWithAdmin();
        FeedSourceDto result = webTestClient.post()
                .uri("/api/feeds")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("sourceUrl", url)
                        .with("description", description))
                .exchange()
                .expectBody(FeedSourceDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(result.getSourceUrl()).isEqualTo(url);
        assertThat(result.getDescription()).isEqualTo(description);
    }

    @Test
    void 둘중_하나의_값이라도_비어있다면_400에러를_리턴한다() {
        String cookie = loginWithAdmin();
        webTestClient.post()
                .uri("/api/feeds")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void findAllFeedArticles() {
        String cookie = loginWithCrew();
        FeedArticleResponseDtos result = webTestClient.get()
                .uri("/api/feeds?page=1")
                .header("Cookie", cookie)
                .exchange()
                .expectBody(FeedArticleResponseDtos.class)
                .returnResult()
                .getResponseBody();

        assertThat(result.getPageNumber()).isEqualTo(1);
        assertThat(result.getArticles().size()).isNotZero();
    }

    @Test
    void updateFeed() {
        String cookie = loginWithAdmin();
        webTestClient.post()
                .uri("/api/feeds/new")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
