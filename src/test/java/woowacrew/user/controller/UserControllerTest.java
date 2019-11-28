package woowacrew.user.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import woowacrew.common.controller.CommonTestController;
import woowacrew.user.domain.UserUpdateDto;

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

    @Test
    void 정상적으로_유저_추가정보를_저장한다() {
        String cookie = getLoginCookie();
        UserUpdateDto userUpdateDto = new UserUpdateDto("test", "1995-06-08");

        webTestClient.post()
                .uri("/users/1/update")
                .header("Cookie", cookie)
                .body(Mono.just(userUpdateDto), UserUpdateDto.class)
                .exchange()
                .expectStatus().is3xxRedirection();
    }
}
