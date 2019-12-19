package woowacrew.article.anonymous.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import woowacrew.common.controller.CommonTestController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnonymousArticleControllerTest extends CommonTestController {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 익명_게시글_생성_테스트() {
        String cookie = loginWithCrew();
        String title = "title";
        String content = "content";
        String password = "password";

        webTestClient.post()
                .uri("/api/articles/anonymous")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("title", title)
                        .with("content", content)
                        .with("password", password))
                .exchange()
                .expectStatus().isCreated();
    }
}
