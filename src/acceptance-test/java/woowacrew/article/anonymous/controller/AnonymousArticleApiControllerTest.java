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

    private static String TITLE = "title";
    private static String CONTENT = "content";
    private static String SIGNING_KEY = "password";

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 익명_게시글_생성_테스트() {
        String cookie = loginWithCrew();

        webTestClient.post()
                .uri("/api/articles/anonymous")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("title", TITLE)
                        .with("content", CONTENT)
                        .with("signingKey", SIGNING_KEY))
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void 승인된_익명_게시글_조회_테스트() {
        String cookie = loginWithCrew();
        Long approvedId = 1L;

        webTestClient.get()
                .uri("/api/articles/anonymous/{anonymousArticleId}", approvedId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 승인되지_않은_익명_게시글_크루_조회_테스트() {
        String cookie = loginWithCrew();
        Long unapprovedId = 4L;

        webTestClient.get()
                .uri("/api/articles/anonymous/{anonymousArticleId}", unapprovedId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void 승인되지_않은_익명_게시글_관리자_조회_테스트() {
        String cookie = loginWithAdmin();
        Long unapprovedId = 4L;

        AnonymousArticleResponseDto anonymousArticle =
                webTestClient.get()
                        .uri("/api/articles/anonymous/{anonymousArticleId}", unapprovedId)
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
    void 승인된_익명_게시글_목록_조회_테스트() {
        String cookie = loginWithCrew();

        AnonymousArticleResponseDtos anonymousArticlesResponseDtos =
                webTestClient.get()
                        .uri("/api/articles/anonymous/approved?page=1")
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
    void 승인되지_않은_익명_게시글을_크루가_조회시_리다이렉트() {
        String cookie = loginWithCrew();

        webTestClient.get()
                .uri("/api/articles/anonymous/unapproved")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    void 승인되지_않은_익명_게시글_목록_조회_테스트() {
        String cookie = loginWithAdmin();

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
    void 올바른_비밀번호로_익명_게시글_수정_테스트() {
        String cookie = loginWithCrew();

        AnonymousArticleResponseDto anonymousArticle = createAnonymousArticle(cookie, TITLE, CONTENT, SIGNING_KEY);

        String updateTitle = "UpdatedTitle";
        String updateContent = "UpdatedContent";

        AnonymousArticleResponseDto updatedAnonymousArticle = webTestClient.put()
                .uri("/api/articles/anonymous/{anonymousArticleId}", anonymousArticle.getAnonymousArticleId())
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("signingKey", SIGNING_KEY)
                        .with("title", updateTitle)
                        .with("content", updateContent))
                .exchange()
                .expectStatus().isOk()
                .expectBody(AnonymousArticleResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(updatedAnonymousArticle.getTitle()).isEqualTo(updateTitle);
        assertThat(updatedAnonymousArticle.getContent()).isEqualTo(updateContent);
    }

    @Test
    void 올바르지_않은_비밀번호로_익명_게시글_수정_테스트() {
        String cookie = loginWithCrew();

        AnonymousArticleResponseDto anonymousArticle = createAnonymousArticle(cookie, TITLE, CONTENT, SIGNING_KEY);

        String updateTitle = "UpdatedTitle";
        String updateContent = "UpdatedContent";

        webTestClient.put()
                .uri("/api/articles/anonymous/{anonymousArticleId}", anonymousArticle.getAnonymousArticleId())
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("signingKey", "invalid password")
                        .with("title", updateTitle)
                        .with("content", updateContent))
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void 올바른_비밀번호로_익명_게시글_삭제_테스트() {
        String cookie = loginWithCrew();

        AnonymousArticleResponseDto anonymousArticle = createAnonymousArticle(cookie, TITLE, CONTENT, SIGNING_KEY);

        webTestClient.put()
                .uri("/api/articles/anonymous/{anonymousArticleId}/delete", anonymousArticle.getAnonymousArticleId())
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("signingKey", SIGNING_KEY))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 올바르지_않은_비밀번호로_익명_게시글_삭제_테스트() {
        String cookie = loginWithCrew();
        String title = "title";
        String content = "content";
        String signingKey = "password";

        AnonymousArticleResponseDto anonymousArticle = createAnonymousArticle(cookie, TITLE, CONTENT, SIGNING_KEY);

        webTestClient.put()
                .uri("/api/articles/anonymous/{anonymousArticleId}/delete", anonymousArticle.getAnonymousArticleId())
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("signingKey", "invalid password"))
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void 익명_게시글의_올바른_비밀번호_확인_테스트() {
        String cookie = loginWithCrew();

        AnonymousArticleResponseDto anonymousArticle = createAnonymousArticle(cookie, TITLE, CONTENT, SIGNING_KEY);

        webTestClient.post()
                .uri("/api/articles/anonymous/{anonymousArticleId}/check", anonymousArticle.getAnonymousArticleId())
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("signingKey", SIGNING_KEY))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 익명_게시글의_잘못된_비밀번호_확인_테스트() {
        String cookie = loginWithCrew();

        AnonymousArticleResponseDto anonymousArticle = createAnonymousArticle(cookie, TITLE, CONTENT, SIGNING_KEY);

        webTestClient.post()
                .uri("/api/articles/anonymous/{anonymousArticleId}/check", anonymousArticle.getAnonymousArticleId())
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("signingKey", "invalid password"))
                .exchange()
                .expectStatus().is5xxServerError();
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

    private AnonymousArticleResponseDto createAnonymousArticle(String cookie, String title, String content, String signingKey) {
        return webTestClient.post()
                .uri("/api/articles/anonymous")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("title", title)
                        .with("content", content)
                        .with("signingKey", signingKey))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AnonymousArticleResponseDto.class)
                .returnResult()
                .getResponseBody();
    }
}
