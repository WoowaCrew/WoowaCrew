package woowacrew.keyword.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import woowacrew.common.controller.CommonTestController;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchApiControllerTest extends CommonTestController {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 정상적으로_검색어를_저장한다() {
        String cookie = loginWithCrew();

        String result = webTestClient.post()
                .uri("/api/search")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("content", "testKeyword"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertThat(result).contains("testKeyword");
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
}
