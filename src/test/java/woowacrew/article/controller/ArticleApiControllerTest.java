package woowacrew.article.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import woowacrew.article.domain.ArticleResponseDto;
import woowacrew.common.controller.CommonTestController;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArticleApiControllerTest extends CommonTestController {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 게시글_목록_조회() {
        String cookie = getLoginCookie();

        List<ArticleResponseDto> articles = webTestClient.get()
                .uri("/api/articles")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ArticleResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(articles.get(0).getTitle()).isEqualTo("article A");
    }

    @Test
    void 게시글_상_조회() {
        String cookie = getLoginCookie();

        ArticleResponseDto article1 = webTestClient.get()
                .uri("/api/articles/1")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ArticleResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(article1.getTitle()).isEqualTo("article A");
        assertThat(article1.getContent()).isEqualTo("content");
    }
}