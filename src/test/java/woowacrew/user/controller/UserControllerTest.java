package woowacrew.user.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import woowacrew.common.controller.CommonTestController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends CommonTestController {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 유저_추가정보_수정_페이지로_이동한다() {
        String cookie = getLoginCookie();

        webTestClient.get()
                .uri("/users/1/form")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 비로그인시_로그인_페이지로_이동한다() {
        webTestClient.get()
                .uri("/users/1/form")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/login"));
    }
}
