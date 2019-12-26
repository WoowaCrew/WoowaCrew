package woowacrew.feed.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import woowacrew.common.controller.CommonTestController;
import woowacrew.feed.dto.FeedSourceResponseDto;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FeedApiControllerTest extends CommonTestController {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void FeedSoruce_목록을_잘불러오는지_테스트() {
        String cookie = loginWithAdmin();
        List<FeedSourceResponseDto> responseDtos = webTestClient.get()
                .uri("/api/feeds")
                .header("Cookie", cookie)
                .exchange()
                .expectBodyList(FeedSourceResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseDtos.size()).isNotZero();
    }

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
        FeedSourceResponseDto result = webTestClient.post()
                .uri("/api/feeds")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("sourceUrl", url)
                        .with("description", description))
                .exchange()
                .expectBody(FeedSourceResponseDto.class)
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
    void updateFeedArticles() {
        String cookie = loginWithAdmin();
        webTestClient.post()
                .uri("/api/feeds/new")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void FeedSource_description_업데이트_테스트() {
        String cookie = loginWithAdmin();
        String updatedDescription = "updatedDescription";
        FeedSourceResponseDto result = webTestClient.put()
                .uri("/api/feeds/1")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("description", updatedDescription))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(FeedSourceResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(result.getDescription()).isEqualTo(updatedDescription);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void delete_Test() throws IOException {
        String cookie = loginWithAdmin();
        String sourceUrl = new ClassPathResource("feed.xml").getURL().toString();
        String description = "description";

        FeedSourceResponseDto result = webTestClient.post()
                .uri("/api/feeds")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("sourceUrl", sourceUrl)
                        .with("description", description))
                .exchange()
                .expectBody(FeedSourceResponseDto.class)
                .returnResult()
                .getResponseBody();

        webTestClient.delete()
                .uri("/api/feeds/" + result.getId())
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
