package woowacrew.keyword.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import woowacrew.common.controller.CommonTestController;
import woowacrew.keyword.domain.KeywordResponseDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchApiControllerTest extends CommonTestController {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 정상적으로_검색어를_저장한다() {
        String cookie = loginWithCrew();

        webTestClient.post()
                .uri("/api/search")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("content", "testKeyword"))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 키워드의_ID로_검색하여_조회수를_늘린다() {
        String cookie = loginWithCrew();

        webTestClient.get()
                .uri("/api/search/1")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void 검색어_순위를_가져온다() {
        String cookie = loginWithCrew();

        List<KeywordResponseDto> result = webTestClient.get()
                .uri("/api/search/rank")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(KeywordResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(result.size()).isNotEqualTo(0);
    }

    @Test
    void 로그인_하지_않으면_검색어를_저장하지_못한다() {
        webTestClient.post()
                .uri("/api/search")
                .body(BodyInserters.fromFormData("content", "test"))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("login"));
    }

    @Test
    void 로그인하지_않으면_검색어_ID로_검색하지_못한다() {
        webTestClient.get()
                .uri("/api/search/1")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("login"));
    }

    @Test
    void 로그인하지_않으면_검색어_순위를_볼수없다() {
        webTestClient.get()
                .uri("/api/search/rank")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("login"));
    }
}
