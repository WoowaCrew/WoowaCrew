package woowacrew.user.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.BodyInserters;
import woowacrew.common.controller.CommonTestController;
import woowacrew.user.dto.UserUpdateDto;
import woowacrew.user.service.exception.InvalidBirthdayException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserControllerTest extends CommonTestController {

    @Test
    void 비로그인시_로그인_페이지로_이동한다() {
        webTestClient.get()
                .uri("/users/form")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/login"));
    }

    @Test
    void 정상적으로_유저_추가정보를_저장한다() {
        String cookie = loginWithCrew();
        UserUpdateDto userUpdateDto = new UserUpdateDto("test", "1995-06-08");

        webTestClient.post()
                .uri("/users/update")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("nickname", "test")
                        .with("birthday", "1995-06-08"))
                .exchange()
                .expectStatus().is3xxRedirection();
    }
}
