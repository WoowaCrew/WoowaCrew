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
    void 게시글_생성_테스트() {
        String cookie = loginWithCrew();
        String title = "title";
        String content = "content";

        webTestClient.post()
                .uri("/api/articles")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("title", title)
                        .with("content", content))
                .exchange()
                .expectStatus().isCreated();
    }

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
    void 게시글_상세_조회() {
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

        Long articleId = createArticle(cookie, "title", "content");

        webTestClient.put()
                .uri("/api/articles/" + articleId)
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("articleId", Long.toString(articleId))
                        .with("title", updateTitle)
                        .with("content", updateContent))
                .exchange()
                .expectStatus().isOk();

        ArticleResponseDto article = webTestClient.get()
                .uri("/api/articles/" + articleId)
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

        Long articleId = createArticle(cookie, "title", "content");

        webTestClient.delete()
                .uri("/api/articles/" + articleId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk();

         webTestClient.get()
                .uri("/api/articles/" + articleId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    private Long createArticle(String cookie, String title, String content) {
        ArticleResponseDto articleResponseDto = webTestClient.post()
                .uri("/api/articles")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("title", title)
                        .with("content", content))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ArticleResponseDto.class)
                .returnResult()
                .getResponseBody();

        assert articleResponseDto != null;

        return articleResponseDto.getId();
    }
}