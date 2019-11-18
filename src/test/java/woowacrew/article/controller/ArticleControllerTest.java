package woowacrew.article.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
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

    @Test
    void 게시글_작성_후_인덱스_페이지로_리다이렉트_한다() {
        String cookie = getLoginCookie();

        webTestClient.post()
                .uri("/articles")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("title", "title")
                        .with("content", "content"))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertThat(body.contains("title")).isTrue();
                    assertThat(body.contains("content")).isTrue();
                });
    }
}