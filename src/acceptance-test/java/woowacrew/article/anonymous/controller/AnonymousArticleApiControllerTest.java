package woowacrew.article.anonymous.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import woowacrew.article.anonymous.dto.AnonymousArticleResponseDto;
import woowacrew.article.anonymous.dto.AnonymousArticleResponseDtos;
import woowacrew.common.controller.CommonTestController;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
public class AnonymousArticleApiControllerTest extends CommonTestController {

    private static String TITLE = "title";
    private static String CONTENT = "content";
    private static String SIGNING_KEY = "password";

    @LocalServerPort
    private String port;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .filter(documentationConfiguration(restDocumentation))
                .build();
    }

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
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(document("anonymous-api/create",
                        requestParameters(
                                parameterWithName("title").description("생성할 익명 게시글 제목"),
                                parameterWithName("content").description("생성할 익명 게시글 내용"),
                                parameterWithName("signingKey").description("익명 게시글 생성 시 패스워드")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("생성된 익명 게시글 경로")
                        ),
                        responseFields(
                                fieldWithPath("anonymousArticleId").description("익명 게시글 번호"),
                                fieldWithPath("title").description("생성된 익명 게시글 제목"),
                                fieldWithPath("content").description("생성된 익명 게시글 내용"),
                                fieldWithPath("isApproved").description("승인 여부"),
                                fieldWithPath("createdDate").ignored(),
                                fieldWithPath("lastModifiedDate").ignored()
                        )
                ));
    }

    @Test
    void 승인된_익명_게시글_조회_테스트() {
        String cookie = loginWithCrew();
        Long approvedId = 1L;

        webTestClient.get()
                .uri("/api/articles/anonymous/{anonymousArticleId}", approvedId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("anonymous-api/read",
                        responseFields(
                                fieldWithPath("anonymousArticleId").description("익명 게시글 번호"),
                                fieldWithPath("title").description("익명 게시글 제목"),
                                fieldWithPath("content").description("익명 게시글 내용"),
                                fieldWithPath("isApproved").description("승인 여부"),
                                fieldWithPath("createdDate").ignored(),
                                fieldWithPath("lastModifiedDate").ignored()
                        )
                ));
    }

    @Test
    void 승인되지_않은_익명_게시글_크루_조회_테스트() {
        String cookie = loginWithCrew();
        Long unapprovedId = 4L;

        webTestClient.get()
                .uri("/api/articles/anonymous/{anonymousArticleId}", unapprovedId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(document("anonymous-api/read-badRequest"));
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
                .consumeWith(document("anonymous-api/update",
                        requestParameters(
                                parameterWithName("title").description("수정할 익명 게시글 제목"),
                                parameterWithName("content").description("수정할 익명 게시글 내용"),
                                parameterWithName("signingKey").description("익명 게시글 수정 시 패스워드")
                        ),
                        responseFields(
                                fieldWithPath("anonymousArticleId").description("수정한 익명 게시글 번호"),
                                fieldWithPath("title").description("수정된 익명 게시글 제목"),
                                fieldWithPath("content").description("수정된 익명 게시글 내용"),
                                fieldWithPath("isApproved").description("승인 여부"),
                                fieldWithPath("createdDate").description("최초 생성 시간"),
                                fieldWithPath("lastModifiedDate").description("최종 수정 시간")
                        )
                ))
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
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("anonymous-api/delete"));
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

    @Test
    void 없는_타입으로_검색하면_예외가_발생한다() {
        String cookie = loginWithCrew();
        String requestType = "zzzz";
        String requestContent = "ti";

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/articles/anonymous/search")
                        .queryParam("page", "1")
                        .queryParam("type", requestType)
                        .queryParam("content", requestContent)
                        .build())
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void 제목으로_정상적으로_검색한다() {
        String cookie = loginWithCrew();
        String requestType = "anonymousArticleTitle";
        String requestContent = "ti";

        AnonymousArticleResponseDtos result = searchAnonymousArticle(cookie, requestType, requestContent);

        List<AnonymousArticleResponseDto> articles = result.getArticles();
        assertTrue(articles.size() != 0);
        for (AnonymousArticleResponseDto article : articles) {
            assertTrue(article.getTitle().contains(requestContent));
        }
    }

    @Test
    void 제목과_내용으로_정상적으로_검색한다() {
        String cookie = loginWithCrew();
        String requestType = "anonymousArticleTitleWithContent";
        String requestContent = "co";

        AnonymousArticleResponseDtos result = searchAnonymousArticle(cookie, requestType, requestContent);

        List<AnonymousArticleResponseDto> articles = result.getArticles();
        assertTrue(articles.size() != 0);
        for (AnonymousArticleResponseDto article : articles) {
            assertTrue(article.getTitle().contains(requestContent) || article.getContent().contains(requestContent));
        }
    }

    private AnonymousArticleResponseDtos searchAnonymousArticle(String cookie, String requestType, String requestContent) {
        return webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/articles/anonymous/search")
                        .queryParam("page", "1")
                        .queryParam("type", requestType)
                        .queryParam("content", requestContent)
                        .build())
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AnonymousArticleResponseDtos.class)
                .returnResult()
                .getResponseBody();
    }
}
