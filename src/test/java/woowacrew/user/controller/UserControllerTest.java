package woowacrew.user.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import woowacrew.common.controller.CommonTestController;
import woowacrew.user.domain.UserUpdateDto;
import woowacrew.user.service.exception.InvalidBirthdayException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends CommonTestController {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 유저_추가정보_수정_페이지로_이동한다() {
        String cookie = loginWithUser();

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
        String cookie = loginWithUser();
        UserUpdateDto userUpdateDto = new UserUpdateDto("test", "1995-06-08");

        webTestClient.post()
                .uri("/users/1/update")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("nickname", "test")
                        .with("birthday", "1995-06-08"))
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    void 올바르지_않은_추가정보_입력값을_받을때_수정_폼에_에러메세지를_포함한다() {
        String cookie = loginWithUser();
        String redirectLocation = "/users/1/form";

        webTestClient.post()
                .uri("/users/1/update")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("nickname", "test")
                        .with("birthday", "1995-13-08"))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString(redirectLocation))
                .expectBody()
                .consumeWith(body -> {
                    String responseBody = webTestClient.get()
                            .uri(redirectLocation)
                            .header("Cookie", cookie)
                            .exchange()
                            .expectBody(String.class)
                            .returnResult()
                            .getResponseBody();

                    assertTrue(responseBody.contains(InvalidBirthdayException.ERROR_MESSAGE));
                });
    }
}
