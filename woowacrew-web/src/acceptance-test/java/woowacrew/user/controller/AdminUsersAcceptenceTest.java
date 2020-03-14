package woowacrew.user.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacrew.common.controller.CommonTestController;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserResponseDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminUsersAcceptenceTest extends CommonTestController {

    @Test
    @DisplayName("관리자의 승인을 받지 않은 유저들을 정상적으로 가져온다.")
    void 승인되지_않은_유저들을_가져온다() {
        String cookie = loginWithAdmin();

        List<UserResponseDto> disapprovedUsers = webTestClient.get()
                .uri("/api/users/disapprove")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserResponseDto.class)
                .returnResult()
                .getResponseBody();

        for (UserResponseDto disapprovedUser : disapprovedUsers) {
            assertTrue(disapprovedUser.getUserRole() == UserRole.ROLE_PRECOURSE);
        }
    }

    @Test
    @DisplayName("관리자의 승인을 받은 유저들을 정상적으로 가져온다.")
    void 승인된_유저들을_가져온다() {
        String cookie = loginWithAdmin();

        List<UserResponseDto> approvedUsers = webTestClient.get()
                .uri("/api/users/approve")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserResponseDto.class)
                .returnResult()
                .getResponseBody();

        for (UserResponseDto approvedUser : approvedUsers) {
            assertTrue(approvedUser.getUserRole() != UserRole.ROLE_PRECOURSE);
        }
    }

    @Test
    @DisplayName("승인되지 않은 유저를 가져오는데 관리자 권한이 아닌 경우 /accessdeny으로 리다이렉트한다.")
    void 관리자가_아닌_경우() {
        String cookie = loginWithCrew();

        webTestClient.get()
                .uri("/api/users/disapprove")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/accessdeny"));
    }

    @Test
    @DisplayName("승인된 유저를 가져오는데 관리자 권한이 아닌 경우 /accessdeny으로 리다이렉트한다.")
    void 승인된_유저를_가져오는데_관리자_권한이_아닌경우() {
        String cookie = loginWithCoach();

        webTestClient.get()
                .uri("/api/users/approve")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/accessdeny"));
    }
}
