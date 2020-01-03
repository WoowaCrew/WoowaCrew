package woowacrew.oauth.controller;

import org.junit.jupiter.api.Test;
import woowacrew.common.controller.CommonTestController;
import woowacrew.user.dto.UserContext;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginApiControllerTest extends CommonTestController {
    @Test
    void 로그인이_되어있지_않다면_401에러를_보낸다() {
        webTestClient.get()
                .uri("/login/info")
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    void 로그인이_되어있다면_UserContext를_리턴() {
        String cookie = loginWithCrew();
        UserContext userContext = webTestClient.get()
                .uri("/login/info")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UserContext.class)
                .returnResult()
                .getResponseBody();

        assertThat(userContext).isNotNull();
    }
}
