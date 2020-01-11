package woowacrew.article.crew.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.BodyInserters;
import woowacrew.article.free.dto.ArticleResponseDto;
import woowacrew.article.free.dto.ArticleResponseDtos;
import woowacrew.common.controller.CommonTestController;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

public class CrewArticleApiControllerTest extends CommonTestController {

    @Test
    void 게시글_목록_불러오기테스트_1페이지() {
        String cookie = loginWithCrew("1234");
        ArticleResponseDtos articleResponseDtos = webTestClient.get()
                .uri("/api/articles/crew?page=1")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ArticleResponseDtos.class)
                .consumeWith(document("crew-api/list",
                        requestParameters(
                                parameterWithName("page").description("페이지 넘버")
                        ),
                        responseFields(
                                fieldWithPath("pageNumber").description("페이지 넘버"),
                                fieldWithPath("totalPages").description("전체 페이지 수"),
                                fieldWithPath("articles[].id").description("게시글 넘버"),
                                fieldWithPath("articles[].title").description("게시글 제목"),
                                fieldWithPath("articles[].content").description("게시글 내용"),
                                fieldWithPath("articles[].userResponseDto.id").description("유저 아이디"),
                                fieldWithPath("articles[].userResponseDto.oauthId").description("Oauth 아이디"),
                                fieldWithPath("articles[].userResponseDto.nickname").description("유저 닉네임").optional(),
                                fieldWithPath("articles[].userResponseDto.birthday").description("유저 생일").optional(),
                                fieldWithPath("articles[].userResponseDto.userRole").description("유저 권한").optional(),
                                fieldWithPath("articles[].userResponseDto.degreeResponseDto.id").description("유저 기수 넘버").optional(),
                                fieldWithPath("articles[].userResponseDto.degreeResponseDto.degreeNumber").description("유저 기수").optional(),
                                fieldWithPath("articles[].createdDate").description("게시글 생성 시간").optional(),
                                fieldWithPath("articles[].lastModifiedDate").description("게시글 최종 수정 시간").optional()
                        )
                ))
                .returnResult()
                .getResponseBody();

        assertThat(articleResponseDtos.getPageNumber()).isNotZero();
        assertThat(articleResponseDtos.getTotalPages()).isNotZero();
        assertThat(articleResponseDtos.getArticles().size()).isNotZero();
    }

    @Test
    void 상세_게시글_정보를_정상적으로_불러오는지_테스트() {
        String cookie = loginWithCrew("1234");
        ArticleResponseDto articleResponseDto = webTestClient.get()
                .uri("/api/articles/crew/1")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ArticleResponseDto.class)
                .consumeWith(document("crew-api/read",
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

        assertThat(articleResponseDto.getTitle()).isEqualTo("1기 게시글");
    }

    @Test
    void 게시글_생성_테스트() {
        String cookie = loginWithCrew("1234");
        webTestClient.post()
                .uri("/api/articles/crew")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("title", "1기 게시글")
                        .with("content", "1기 게시글 테스트"))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectHeader()
                .value("Location", Matchers.containsString("/articles/crew"))
                .expectBody()
                .consumeWith(document("crew-api/create",
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
    void 수정_테스트() {
        String cookie = loginWithCrew();
        ArticleResponseDto articleResponseDto = createCrewArticle(cookie);

        ArticleResponseDto result = webTestClient.put()
                .uri("/api/articles/crew/" + articleResponseDto.getId())
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("articleId", String.valueOf(articleResponseDto.getId()))
                        .with("title", "수정된 타이틀")
                        .with("content", "수정된 컨텐츠"))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ArticleResponseDto.class)
                .consumeWith(document("crew-api/update",
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
                ))
                .returnResult()
                .getResponseBody();
        assertThat(result.getId()).isEqualTo(articleResponseDto.getId());
        assertThat(result.getTitle()).isEqualTo("수정된 타이틀");
    }

    @Test
    void 삭제_테스트() {
        String cookie = loginWithCrew();
        ArticleResponseDto articleResponseDto = createCrewArticle(cookie);

        webTestClient.delete()
                .uri("/api/articles/crew/" + articleResponseDto.getId())
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(document("crew-api/delete"));
    }

    private ArticleResponseDto createCrewArticle(String cookie) {
        return webTestClient.post()
                .uri("/api/articles/crew")
                .header("Cookie", cookie)
                .body(BodyInserters.fromFormData("title", "임시 게시글")
                        .with("content", "임시 게시글 테스트"))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(ArticleResponseDto.class)
                .returnResult()
                .getResponseBody();
    }

    @Test
    void 정상적으로_자신의_기수에_맞는_게시글을_제목_검색한다() {
        String cookie = loginWithCrew();
        String inputData = "1기";

        ArticleResponseDtos articleResponseDtos = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/articles/crew/search")
                        .queryParam("type", "title")
                        .queryParam("content", inputData)
                        .build())
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ArticleResponseDtos.class)
                .returnResult()
                .getResponseBody();

        List<ArticleResponseDto> articles = articleResponseDtos.getArticles();
        assertTrue(articles.size() != 0);
        for (ArticleResponseDto article : articles) {
            assertTrue(article.getTitle().contains(inputData));
        }
    }

    @Test
    void 정상적으로_자신의_기수에_맞는_게시글을_제목과_내용으로_검색한다() {
        String cookie = loginWithCrew();
        String inputData = "1기";

        ArticleResponseDtos articleResponseDtos = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/articles/crew/search")
                        .queryParam("type", "titleWithContent")
                        .queryParam("content", inputData)
                        .build())
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ArticleResponseDtos.class)
                .returnResult()
                .getResponseBody();

        List<ArticleResponseDto> articles = articleResponseDtos.getArticles();
        assertTrue(articles.size() != 0);
        for (ArticleResponseDto article : articles) {
            assertTrue(article.getTitle().contains(inputData) || article.getContent().contains(inputData));
        }
    }

    @Test
    void 정상적으로_자신의_기수에_맞는_게시글을_작성자로_검색한다() {
        String cookie = loginWithCrew();
        String inputData = "woowacrew";

        ArticleResponseDtos articleResponseDtos = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/articles/crew/search")
                        .queryParam("type", "author")
                        .queryParam("content", inputData)
                        .build())
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ArticleResponseDtos.class)
                .returnResult()
                .getResponseBody();

        List<ArticleResponseDto> articles = articleResponseDtos.getArticles();
        assertTrue(articles.size() != 0);
        for (ArticleResponseDto article : articles) {
            assertTrue(article.getUserResponseDto().getNickname().contains(inputData));
        }
    }

    @Test
    void 로그인_하지_않고_검색하는_경우_로그인_페이지로_이동한다() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/articles/crew/search")
                        .queryParam("type", "title")
                        .queryParam("content", "1기")
                        .build())
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/login"));
    }
}
