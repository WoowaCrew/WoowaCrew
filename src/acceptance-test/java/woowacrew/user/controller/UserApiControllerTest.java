package woowacrew.user.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import woowacrew.common.controller.CommonTestController;
import woowacrew.user.dto.UserResponseDto;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserApiControllerTest extends CommonTestController {

    @Test
    void 정상적으로_유저정보_찾는다() {
        String cookie = loginWithCrew();

        UserResponseDto result = webTestClient.get()
                .uri("/api/user/info")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(result);
    }

    @Test
    void 로그인_되어_있지_않으면_유저정보를_찾지_못한다() {
        webTestClient.get()
                .uri("/api/user/info")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/login"));
    }
}