package woowacrew.article.free.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.BodyInserters;
import woowacrew.article.free.dto.ArticleResponseDto;
import woowacrew.common.controller.CommonTestController;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacrew.article.free.controller.ArticleApiControllerTest.createArticle;

class ArticleControllerTest extends CommonTestController {

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
}