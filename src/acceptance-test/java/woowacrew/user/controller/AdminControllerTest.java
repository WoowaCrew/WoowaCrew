package woowacrew.user.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import woowacrew.common.controller.CommonTestController;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AdminControllerTest extends CommonTestController {

    @Test
    void admin이_아닐시_redirection발생() {
        String cookie = loginWithCrew();

        webTestClient.get()
                .uri("/admin")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/accessdeny"));
    }

    @Test
    void admin이면_admin페이지가_보여진다() {
        String cookie = loginWithAdmin();

        webTestClient.get()
                .uri("/admin")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertTrue(body.contains("관리자 페이지"));
                });
    }
}