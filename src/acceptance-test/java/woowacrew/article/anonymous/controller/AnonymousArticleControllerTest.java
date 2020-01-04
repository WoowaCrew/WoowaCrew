package woowacrew.article.anonymous.controller;

import org.junit.jupiter.api.Test;
import woowacrew.common.controller.CommonTestController;

public class AnonymousArticleControllerTest extends CommonTestController {

    @Test
    void 익명_게시판_페이지로_이동() {
        String cookie = loginWithCrew();

        webTestClient.get()
                .uri("/articles/anonymous")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 익명_게시글_작성_페이지로_이동() {
        String cookie = loginWithCrew();

        webTestClient.get()
                .uri("/articles/anonymous/new")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 익명_게시글_수정_페이지로_이동() {
        String cookie = loginWithCrew();

        webTestClient.get()
                .uri("/articles/anonymous/1/edit")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk();
    }
}
