package woowacrew.user.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import woowacrew.common.controller.CommonTestController;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserApproveDto;

public class AdminApproveAcceptenceTest extends CommonTestController {

    @Test
    void 관리자가_권한_변경_요청을_보내면_해당_id의_유저_권한을_변경한다() {
        String adminCookie = loginWithAdmin();
        UserApproveDto userApproveDto = new UserApproveDto(UserRole.ROLE_CREW, 1);

        webTestClient.put()
                .uri("/api/users/{id}/approve", 3)
                .body(Mono.just(userApproveDto), UserApproveDto.class)
                .header("Cookie", adminCookie)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 관리자_권한이_없는데_유저_권한을_수정하는_경우() {
        String crewCookie = loginWithCrew();
        UserApproveDto userApproveDto = new UserApproveDto(UserRole.ROLE_CREW, 1);

        webTestClient.put()
                .uri("/api/users/{id}/approve", 2)
                .body(Mono.just(userApproveDto), UserApproveDto.class)
                .header("Cookie", crewCookie)
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/accessdeny"));
    }

    @Test
    void 없는_유저의_권한을_수정하는_경우_에러페이지로_이동한다() {
        String adminCookie = loginWithAdmin();
        UserApproveDto userApproveDto = new UserApproveDto(UserRole.ROLE_CREW, 1);
        int notExistUserId = 0;

        webTestClient.put()
                .uri("/api/users/{id}/approve", notExistUserId)
                .body(Mono.just(userApproveDto), UserApproveDto.class)
                .header("Cookie", adminCookie)
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/error"));
    }

    @Test
    void 없는_기수의_유저의_권한을_수정하는_경우_에러페이지로_이동한다() {
        String adminCookie = loginWithAdmin();
        int underBoundDegreeNumber = -1;
        UserApproveDto userApproveDto = new UserApproveDto(UserRole.ROLE_CREW, underBoundDegreeNumber);

        webTestClient.put()
                .uri("/api/users/{id}/approve", 2)
                .body(Mono.just(userApproveDto), UserApproveDto.class)
                .header("Cookie", adminCookie)
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/error"));
    }
}
