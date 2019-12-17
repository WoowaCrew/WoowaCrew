package woowacrew.search.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import woowacrew.article.free.dto.ArticleResponseDto;
import woowacrew.article.free.dto.ArticleResponseDtos;
import woowacrew.common.controller.CommonTestController;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleSearchApiControllerTest extends CommonTestController {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void 정상적으로_게시글_제목으로_검색한다() {
        String cookie = loginWithAdmin();

        ArticleResponseDtos articleResponseDtos = webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/articles/search")
                        .queryParam("type", "title")
                        .build())
                .body(BodyInserters.fromFormData("content", "delete"))
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ArticleResponseDtos.class)
                .returnResult()
                .getResponseBody();

        assert articleResponseDtos != null;
        List<ArticleResponseDto> articles = articleResponseDtos.getArticles();

        assertTrue(articles.size() != 0);
        articles.forEach(article -> assertTrue(article.getTitle().contains("delete")));
    }

    @Test
    void 정상적으로_게시글_작성자로_검색한다() {
        String cookie = loginWithAdmin();

        ArticleResponseDtos articleResponseDtos = webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/articles/search")
                        .queryParam("type", "author")
                        .build())
                .body(BodyInserters.fromFormData("content", "woowacrew"))
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ArticleResponseDtos.class)
                .returnResult()
                .getResponseBody();

        assert articleResponseDtos != null;
        List<ArticleResponseDto> articles = articleResponseDtos.getArticles();

        assertTrue(articles.size() != 0);
        articles.forEach(article -> assertEquals("woowacrew", article.getUserResponseDto().getNickname()));
    }

    @Test
    void 올바르지_않은_타입으로_검색하면_예외발생() {
        String cookie = loginWithAdmin();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/articles/search")
                        .queryParam("type", "title2")
                        .build())
                .body(BodyInserters.fromFormData("content", "delete"))
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void 권한없는_유저가_검색하는_경우_예외가_발생한() {
        String cookie = loginWithPrecourse();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/articles/search")
                        .queryParam("type", "title")
                        .build())
                .body(BodyInserters.fromFormData("content", "delete"))
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/accessdeny"));
    }
}
