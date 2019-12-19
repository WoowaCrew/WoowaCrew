package woowacrew.article.anonymous.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import woowacrew.common.controller.CommonTestController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnonymousArticleControllerTest extends CommonTestController {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 익명_게시판_페이지로_이동() {
        String cookie = loginWithCrew();

        webTestClient.get()
                .uri("/articles/anonymous")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk();
    }
}
