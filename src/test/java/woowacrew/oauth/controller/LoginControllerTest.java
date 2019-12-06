package woowacrew.oauth.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import woowacrew.common.controller.CommonTestController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.config.location=classpath:/github.yml", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest extends CommonTestController {

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
                    assertThat(body.contains("/login/github")).isTrue();
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


    @Test
    @DisplayName("/login/github 요청시 client_id를 포함한 redirect url을 응답한다")
    void loginTest3() {
        webTestClient.get()
                .uri("/login/github")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("https://github.com/login/oauth/authorize?client_id="));
    }

    @Test
    @DisplayName("승인이 나지 않은 사람이 /aritcle/new에 접근하면 accessdeny된다.")
    void accessDenyTest() {
        String cookie = loginWithPrecourse();

        webTestClient.get()
                .uri("/article/new")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/accessdeny"));
    }

    @Test
    @DisplayName("닉네임을 입력하지 않은 사람이 /article/new에 접근하면 닉네임 입력하라고 나온다.")
    void accessDenyTest2() {
        String cookie = loginWithPrecourse();

        webTestClient.get()
                .uri("/accessdeny")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertThat(body.contains("닉네임")).isTrue();
                });
    }
}