package woowacrew.article.free.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import woowacrew.article.free.dto.ArticleResponseDto;
import woowacrew.article.free.dto.ArticleResponseDtos;
import woowacrew.article.free.service.ArticleInternalService;
import woowacrew.common.controller.CommonTestController;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

class ArticleApiControllerTest extends CommonTestController {

    private static final String TYPE = "type";
    private static final String CONTENT = "content";

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void 게시글_생성_테스트() {
        String cookie = loginWithCrew();
        String title = "title";
        String content = "content";

        webTestClient.post()
                .uri("/api/articles")
                .header("Cookie", cookie)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromFormData("title", title)
                        .with("content", content))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(document("free-api/create",
                        requestParameters(
                                parameterWithName("title").description("게시글 제목"),
                                parameterWithName("content").description("게시글 내용")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("생성된 게시글 경로")
                        ),
                        responseFields(
                                fieldWithPath("id").description("게시글 아이디"),
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용"),
                                fieldWithPath("userResponseDto.id").description("유저 아이디"),
                                fieldWithPath("userResponseDto.oauthId").ignored(),
                                fieldWithPath("userResponseDto.nickname").description("유저 닉네임"),
                                fieldWithPath("userResponseDto.birthday").description("유저 생일"),
                                fieldWithPath("userResponseDto.userRole").description("유저 권한"),
                                fieldWithPath("userResponseDto.degreeResponseDto.*").description("유저 기수"),
                                fieldWithPath("createdDate").description("게시글 생성 시간"),
                                fieldWithPath("lastModifiedDate").description("게시글 최종 수정 시간")
                        )
                ));
    }

    @Test
    void 게시글_목록_조회() {
        String cookie = loginWithCrew();

        ArticleResponseDtos articles = webTestClient.get()
                .uri("/api/articles?page=1")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ArticleResponseDtos.class)
                .consumeWith(document("free-api/list",
                        requestParameters(
                                parameterWithName("page").description("페이지 넘버")
                        )
                ))
                .returnResult()
                .getResponseBody();

        assertThat(articles.getArticles().size()).isLessThanOrEqualTo(ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE);
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
                .consumeWith(document("free-api/read",
                        responseFields(
                                fieldWithPath("id").description("게시글 아이디"),
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용"),
                                fieldWithPath("userResponseDto.id").description("유저 아이디"),
                                fieldWithPath("userResponseDto.oauthId").ignored(),
                                fieldWithPath("userResponseDto.nickname").description("유저 닉네임"),
                                fieldWithPath("userResponseDto.birthday").description("유저 생일"),
                                fieldWithPath("userResponseDto.userRole").description("유저 권한"),
                                fieldWithPath("userResponseDto.degreeResponseDto.*").description("유저 기수"),
                                fieldWithPath("createdDate").description("게시글 생성 시간"),
                                fieldWithPath("lastModifiedDate").description("게시글 최종 수정 시간")
                        )
                ))
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

        Long articleId = createArticle(webTestClient, cookie, "title", "content");

        webTestClient.put()
                .uri("/api/articles/" + articleId)
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("articleId", Long.toString(articleId))
                        .with("title", updateTitle)
                        .with("content", updateContent))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ArticleResponseDto.class)
                .consumeWith(document("free-api/update",
                        requestParameters(
                                parameterWithName("articleId").description("수정할 게시글 번호"),
                                parameterWithName("title").description("수정할 게시글 제목"),
                                parameterWithName("content").description("수정할 게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("id").description("게시글 아이디"),
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용"),
                                fieldWithPath("userResponseDto.id").description("유저 아이디"),
                                fieldWithPath("userResponseDto.oauthId").ignored(),
                                fieldWithPath("userResponseDto.nickname").description("유저 닉네임"),
                                fieldWithPath("userResponseDto.birthday").description("유저 생일"),
                                fieldWithPath("userResponseDto.userRole").description("유저 권한"),
                                fieldWithPath("userResponseDto.degreeResponseDto.*").description("유저 기수"),
                                fieldWithPath("createdDate").description("게시글 생성 시간"),
                                fieldWithPath("lastModifiedDate").description("게시글 최종 수정 시간")
                        )
                ));

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

        Long articleId = createArticle(webTestClient, cookie, "title", "content");

        webTestClient.delete()
                .uri("/api/articles/" + articleId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("free-api/delete"));

        webTestClient.get()
                .uri("/api/articles/" + articleId)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    public static Long createArticle(WebTestClient webTestClient, String cookie, String title, String content) {
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

    @Test
    void 정상적으로_게시글_제목으로_검색한다() {
        String cookie = loginWithAdmin();
        String inputData = "spec";

        ArticleResponseDtos articleResponseDtos = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/articles/search")
                        .queryParam(TYPE, "title")
                        .queryParam(CONTENT, inputData)
                        .build())
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ArticleResponseDtos.class)
                .returnResult()
                .getResponseBody();

        assert articleResponseDtos != null;
        List<ArticleResponseDto> articles = articleResponseDtos.getArticles();

        assertTrue(articles.size() != 0);
        articles.forEach(article -> assertTrue(article.getTitle().contains(inputData)));
    }

    @Test
    void 정상적으로_게시글_작성자로_검색한다() {
        String cookie = loginWithAdmin();
        String inputData = "woowacrew";

        ArticleResponseDtos articleResponseDtos = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/articles/search")
                        .queryParam(TYPE, "author")
                        .queryParam(CONTENT, inputData)
                        .build())
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ArticleResponseDtos.class)
                .returnResult()
                .getResponseBody();

        assert articleResponseDtos != null;
        List<ArticleResponseDto> articles = articleResponseDtos.getArticles();

        assertTrue(articles.size() != 0);
        articles.forEach(article -> assertEquals(inputData, article.getUserResponseDto().getNickname()));
    }

    @Test
    void 정상적으로_게시글_제목과_내용으로_검색한다() {
        String cookie = loginWithAdmin();
        String inputData = "spec";

        ArticleResponseDtos articleResponseDtos = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/articles/search")
                        .queryParam(TYPE, "titleWithContent")
                        .queryParam(CONTENT, inputData)
                        .build())
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ArticleResponseDtos.class)
                .returnResult()
                .getResponseBody();

        assert articleResponseDtos != null;
        List<ArticleResponseDto> articles = articleResponseDtos.getArticles();

        assertTrue(articles.size() != 0);
        articles.forEach(article -> assertTrue(article.getTitle().contains(inputData) ||
                article.getContent().contains(inputData)));
    }

    @Test
    void 올바르지_않은_타입으로_검색하면_예외발생() {
        String cookie = loginWithAdmin();
        String inputData = "spec";

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/articles/search")
                        .queryParam(TYPE, "title2")
                        .queryParam(CONTENT, inputData)
                        .build())
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void 권한없는_유저가_검색하는_경우_예외가_발생한다() {
        String cookie = loginWithPrecourse();
        String inputData = "spec";

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/articles/search")
                        .queryParam(TYPE, "title")
                        .queryParam(CONTENT, inputData)
                        .build())
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/accessdeny"));
    }
}