package woowacrew.feed.controller;


import org.junit.jupiter.api.Test;
import woowacrew.common.controller.CommonTestController;
import woowacrew.feed.dto.FeedArticleResponseDto;
import woowacrew.feed.dto.FeedArticleResponseDtos;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FeedArticlesApiControllerTest extends CommonTestController {

    @Test
    void findAllFeedArticles() {
        String cookie = loginWithCrew();
        FeedArticleResponseDtos result = webTestClient.get()
                .uri("/api/feed-articles?page=1")
                .header("Cookie", cookie)
                .exchange()
                .expectBody(FeedArticleResponseDtos.class)
                .returnResult()
                .getResponseBody();

        assertThat(result.getPageNumber()).isEqualTo(1);
        assertThat(result.getArticles().size()).isNotZero();
    }

    @Test
    void 제목으로_정상적으로_검색한다() {
        String cookie = loginWithCrew();
        String requestSearchType = "feedTitle";
        String requestSearchContent = "title";
        FeedArticleResponseDtos result = searchFeedArticles(cookie, requestSearchType, requestSearchContent);

        List<FeedArticleResponseDto> feedArticles = result.getArticles();
        assertTrue(feedArticles.size() != 0);
        for (FeedArticleResponseDto feedArticle : feedArticles) {
            assertThat(feedArticle.getTitle()).contains(requestSearchContent);
        }
    }

    @Test
    void 블로그로_정상적으로_검색한다() {
        String cookie = loginWithCrew();
        String requestSearchType = "feedDescription";
        String requestSearchContent = "SHAKEVAN";
        FeedArticleResponseDtos result = searchFeedArticles(cookie, requestSearchType, requestSearchContent);

        List<FeedArticleResponseDto> feedArticles = result.getArticles();
        assertTrue(feedArticles.size() != 0);
        for (FeedArticleResponseDto feedArticle : feedArticles) {
            assertThat(feedArticle.getFeedSourceDto().getDescription()).contains(requestSearchContent);
        }
    }

    private FeedArticleResponseDtos searchFeedArticles(String cookie, String searchType, String searchContent) {
        return webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/feed-articles/search")
                        .queryParam("type", searchType)
                        .queryParam("content", searchContent)
                        .queryParam("page", "1")
                        .build())
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody(FeedArticleResponseDtos.class)
                .returnResult()
                .getResponseBody();
    }
}
