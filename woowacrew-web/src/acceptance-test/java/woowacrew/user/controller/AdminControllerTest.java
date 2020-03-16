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
}