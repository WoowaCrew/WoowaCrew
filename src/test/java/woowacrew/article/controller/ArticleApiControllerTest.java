package woowacrew.article.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
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
        String cookie = loginWithCrew();

        List<ArticleResponseDto> articles = webTestClient.get()
                .uri("/api/articles")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ArticleResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(articles.get(articles.size() - 1).getTitle()).isEqualTo("article A");
    }

    @Test
    void 게시글_상_조회() {
        String cookie = loginWithCrew();

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

    @Test
    void 게시글_수정() {
        String cookie = loginWithCrew();

        String updateTitle = "Update Title";
        String updateContent = "Update Content";

        webTestClient.put()
                .uri("/api/articles/1")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("articleId", "1")
                        .with("title", updateTitle)
                        .with("content", updateContent))
                .exchange()
                .expectStatus().isOk();

        ArticleResponseDto article = webTestClient.get()
                .uri("/api/articles/1")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ArticleResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(article.getTitle()).isEqualTo(updateTitle);
        assertThat(article.getContent()).isEqualTo(updateContent);
    }

    @Test
    void 게시글_삭제() {
        String cookie = loginWithCrew();

        webTestClient.delete()
                .uri("/api/articles/2")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk();

         webTestClient.get()
                .uri("/api/articles/2")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }
}