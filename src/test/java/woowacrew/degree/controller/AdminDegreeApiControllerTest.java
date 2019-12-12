package woowacrew.degree.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import woowacrew.common.controller.CommonTestController;
import woowacrew.degree.dto.DegreeWithUserCountResponseDto;
import woowacrew.user.dto.UserResponseDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminDegreeApiControllerTest extends CommonTestController {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 관리자가_아니면_접근이_거부된다() {
        String cookie = loginWithCrew();
        webTestClient.get()
                .uri("/api/degrees")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/accessdeny"));
    }

    @Test
    void 기수별_유저_수_리턴_테스트() {
        String cookie = loginWithAdmin();
        List<DegreeWithUserCountResponseDto> response = webTestClient.get()
                .uri("/api/degrees")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(DegreeWithUserCountResponseDto.class)
                .returnResult()
                .getResponseBody();

        DegreeWithUserCountResponseDto course1 = response.get(1);
        assertThat(course1.getUserCount()).isNotZero();
    }

    @Test
    void 기수별_유저_목록_리턴_테스트() {
        String cookie = loginWithAdmin();
        List<UserResponseDto> response = webTestClient.get()
                .uri("/api/degrees/1/users")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(UserResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(response.size()).isNotZero();
    }
}