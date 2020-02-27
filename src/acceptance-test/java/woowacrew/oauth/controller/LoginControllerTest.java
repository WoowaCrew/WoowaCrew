package woowacrew.oauth.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import woowacrew.common.controller.CommonTestController;

class LoginControllerTest extends CommonTestController {

    @Test
    @DisplayName("로그인이 안된 상태로 인덱스, 로그인 페이지가 아닌 다른 페이지 요청 시 /login으로 리다이렉트 한다")
    void loginPageRedirectTest() {
        webTestClient.get()
                .uri("/articles")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/login"));
    }


    @Test
    @DisplayName("/login/github 요청시 client_id를 포함한 redirect url을 응답한다")
    void githubLoginPageRedirectTest() {
        webTestClient.get()
                .uri("/login/github")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("https://github.com/login/oauth/authorize?client_id="));
    }

    @Test
    @DisplayName("승인이 나지 않은 사람이 /aritcle/new에 접근하면 accessdeny된다.")
    void accessDenyTestWithNotApprove() {
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
    void 로그아웃시_200을_리턴한다() {
        String cookie = loginWithPrecourse();

        webTestClient.get()
                .uri("/logout")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk();
    }
}