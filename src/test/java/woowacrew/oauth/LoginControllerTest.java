package woowacrew.oauth;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import woowacrew.oauth.github.GithubConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private GithubConfig githubConfig;

    @Test
    void test() {
        System.out.println(githubConfig.getClientId());
    }

    @Test
    void 로그인_요청시_github_Login으로_리다이렉트한다() {
        webTestClient.get()
                .uri("/login")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("https://github.com/login/oauth/authorize"));
    }
}