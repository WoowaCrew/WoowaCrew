package woowacrew.oauth;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 로그인_요청시_github_Login으로_리다이렉트한다() {
        webTestClient.get()
                .uri("/login")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("https://github.com/login/oauth/authorize"));
    }

    @Test
    void 인증_허가시_깃헙_코드를_받아온다() {
        webTestClient.get()
                .uri("/oauth/github?code=test")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/"));
    }
}