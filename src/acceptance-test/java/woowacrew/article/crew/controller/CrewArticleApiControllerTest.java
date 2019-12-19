package woowacrew.article.crew.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.BodyInserters;
import woowacrew.article.free.dto.ArticleResponseDto;
import woowacrew.article.free.dto.ArticleResponseDtos;
import woowacrew.common.controller.CommonTestController;

import static org.assertj.core.api.Assertions.assertThat;

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
                .value("Location", Matchers.containsString("/articles/crew"));
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
                .isOk();
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
}
