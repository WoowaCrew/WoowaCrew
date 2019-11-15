package woowacrew.article.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import woowacrew.common.controller.CommonTestController;
import woowacrew.utils.configuration.WebMvcConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(WebMvcConfig.class)
class ArticleControllerTest extends CommonTestController {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 로그인이_되어있지_않으면_로그인페이지가_리다이렉트() {
        webTestClient.get()
                .uri("/article/new")
                .exchange()
                .expectStatus()
                .is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/login"));
    }

    @Test
    void 로그인이_되어있다면_게시글_작성_페이지가_보여진다() {
        String cookie = getLoginCookie();

        webTestClient.get()
                .uri("/article/new")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = response.toString();
                    assertThat(body.contains("게시글 작성")).isTrue();
                });
    }
}