package woowacrew.oauth.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.config.location=classpath:/github.yml", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("/login으로 요청시 로그인 방법을 선택할 수 있는 창이 표시된다.")
    void loginTest() {
        webTestClient.get()
                .uri("/login")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertThat(body.contains("https://github.com/login/oauth/authorize?clientId=")).isTrue();
                });
    }

    @Test
    @DisplayName("로그인이 안된 상태로 인덱스, 로그인 페이지가 아닌 다른 페이지 요청 시 /login으로 리다이렉트 한다")
    void loginTest2() {
        webTestClient.get()
                .uri("/articles")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/login"));
    }
}