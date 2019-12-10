package woowacrew.article.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import woowacrew.article.dto.ArticleResponseDto;
import woowacrew.common.controller.CommonTestController;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacrew.article.controller.ArticleApiControllerTest.createArticle;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
        String cookie = loginWithCrew();
        webTestClient
                .get()
                .uri("/article/new")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = response.toString();
                    assertThat(body.contains("/js/article-create.js")).isTrue();
                });
    }

    @Test
    void 게시글_작성_후_해당_201응답이다() {
        String cookie = loginWithCrew();
        ArticleResponseDto articleResponseDto = webTestClient.post()
                .uri("/api/articles")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("title", "title")
                        .with("content", "content"))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .value("Location", Matchers.containsString("/articles"))
                .expectBody(ArticleResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(articleResponseDto.getTitle()).isEqualTo("title");
        assertThat(articleResponseDto.getContent()).isEqualTo("content");
    }

    @Test
    void 존재하는_게시글_번호일시_페이지_테스트() {
        String cookie = loginWithCrew();
        Long articleId = createArticle(webTestClient, cookie, "title", "title");

        webTestClient.get()
                .uri("/articles/" + articleId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .consumeWith(viewBody -> {
                    String actualBody = new String(viewBody.getResponseBody());
                    assertThat(actualBody.contains("<body>")).isTrue();
                });
    }

    @Test
    void 전체_게시글_조회_테스트() {
        String cookie = loginWithCrew();
        saveArticle(2, cookie);
        webTestClient.get()
                .uri("/articles")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertThat(body.contains("title")).isTrue();
                });
    }

    @Test
    void 작성자인_경우_수정_페이지로_이동_가능() {
        String cookie = loginWithCrew();
        Long articleId = createArticle(webTestClient, cookie, "title", "content");

        webTestClient.get()
                .uri("/articles/{articleId}/edit", articleId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 작성자가_아닌_경우_수정_페이지로_이동_불가능() {
        String cookie = loginWithCrew("1");
        String otherCookie = loginWithCrew("2");
        Long articleId = createArticle(webTestClient, cookie, "title", "content");

        webTestClient.get()
                .uri("/articles/{articleId}/edit", articleId)
                .header("Cookie", otherCookie)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    private void saveArticle(int numberOfArticle, String cookie) {
        for (int i = 0; i < numberOfArticle; i++) {
            webTestClient.post()
                    .uri("/api/articles")
                    .header("Cookie", cookie)
                    .body(BodyInserters.fromFormData("title", "title")
                            .with("content", "content"))
                    .exchange();
        }
    }
}