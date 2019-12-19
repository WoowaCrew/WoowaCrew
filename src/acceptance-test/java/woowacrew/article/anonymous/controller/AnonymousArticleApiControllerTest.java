package woowacrew.article.anonymous.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import woowacrew.article.anonymous.dto.AnonymousArticleResponseDto;
import woowacrew.article.anonymous.dto.AnonymousArticleResponseDtos;
import woowacrew.common.controller.CommonTestController;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnonymousArticleApiControllerTest extends CommonTestController {

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

    @Test
    void 익명_게시글_조회_테스트() {
        String cookie = loginWithCrew();

        AnonymousArticleResponseDto anonymousArticle =
                webTestClient.get()
                        .uri("/api/articles/anonymous/{anonymousArticleId}", 1L)
                        .header("Cookie", cookie)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(AnonymousArticleResponseDto.class)
                        .returnResult()
                        .getResponseBody();

        assertThat(anonymousArticle.getTitle()).isEqualTo("title");
        assertThat(anonymousArticle.getContent()).isEqualTo("content");
    }

    @Test
    void 승인된_익명_게시글_조회_테스트() {
        String cookie = loginWithCrew();

        AnonymousArticleResponseDtos anonymousArticlesResponseDtos =
                webTestClient.get()
                        .uri("/api/articles/anonymous/approved?page=0")
                        .header("Cookie", cookie)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(AnonymousArticleResponseDtos.class)
                        .returnResult()
                        .getResponseBody();

        anonymousArticlesResponseDtos.getArticles()
                .forEach(anonymousArticleResponseDto -> assertTrue(anonymousArticleResponseDto.getIsApproved()));
    }

    @Test
    void 승인되지_않은_익명_게시글_조회_테스트() {
        String cookie = loginWithCrew();

        List<AnonymousArticleResponseDto> anonymousArticles =
                webTestClient.get()
                        .uri("/api/articles/anonymous/unapproved")
                        .header("Cookie", cookie)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(AnonymousArticleResponseDto.class)
                        .returnResult()
                        .getResponseBody();

        anonymousArticles.forEach(anonymousArticleResponseDto ->
                assertFalse(anonymousArticleResponseDto.getIsApproved()));
    }

    @Test
    void 게시글_승인_테스트() {
        Long unapprovedArticleId = 4L;
        String cookie = loginWithAdmin();

        AnonymousArticleResponseDto anonymousArticle =
                webTestClient.put()
                        .uri("/api/articles/anonymous/{anonymousArticleId}/approve", unapprovedArticleId)
                        .header("Cookie", cookie)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(AnonymousArticleResponseDto.class)
                        .returnResult()
                        .getResponseBody();

        assertTrue(anonymousArticle.getIsApproved());
    }
}
