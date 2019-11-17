package woowacrew.keyword.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 검색어를_정상적으로_저장한다() {
        webTestClient.get()
                .uri("/search?content=test")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("google.com"));
    }

    @Test
    void 조회수_순으로_순위를_보여준다() {
        webTestClient.get()
                .uri("/search/rank")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    String responseBody = new String(Objects.requireNonNull(response.getResponseBody()), StandardCharsets.UTF_8);
                    assertThat(responseBody).contains("최다 조회수 A")
                            .contains("최다 조회수 B")
                            .contains("최다 조회수 C");
                });
    }
}