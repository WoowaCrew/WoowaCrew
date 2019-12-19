package woowacrew.article.crew.controller;

import org.junit.jupiter.api.Test;
import woowacrew.common.controller.CommonTestController;

import static org.assertj.core.api.Assertions.assertThat;

public class CrewArticleControllerTest extends CommonTestController {

    @Test
    void 크루_게시글_작성페이지를_잘_불러오는지_테스트() {
        String cookie = loginWithCrew();
        String result = webTestClient.get()
                .uri("/article/crew/new")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertThat(result.contains("article-crew-create")).isTrue();
    }

    @Test
    void 게시글_목록_페이지를_잘불러오는지_테스트() {
        String cookie = loginWithCrew();
        String result = webTestClient.get()
                .uri("/articles/crew")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertThat(result.contains("크루게시판")).isTrue();
    }

    @Test
    void 게시글_상세_정보_페이지() {
        String cookie = loginWithCrew();
        String result = webTestClient.get()
                .uri("/articles/crew/1")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertThat(result.contains("article-crew")).isTrue();
    }

    @Test
    void 게시글_수정_페이지() {
        String cookie = loginWithCrew("1234");
        String result = webTestClient.get()
                .uri("/articles/crew/1/edit")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertThat(result.contains("article-crew-edit")).isTrue();
    }
}
