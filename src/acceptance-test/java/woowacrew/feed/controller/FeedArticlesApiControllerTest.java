package woowacrew.feed.controller;


import org.junit.jupiter.api.Test;
import woowacrew.common.controller.CommonTestController;
import woowacrew.feed.dto.FeedArticleResponseDto;
import woowacrew.feed.dto.FeedArticleResponseDtos;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

public class FeedArticlesApiControllerTest extends CommonTestController {

    @Test
    void findAllFeedArticles() {
        String cookie = loginWithCrew();
        FeedArticleResponseDtos result = webTestClient.get()
                .uri("/api/feed-articles?page=1")
                .header("Cookie", cookie)
                .exchange()
                .expectBody(FeedArticleResponseDtos.class)
                .consumeWith(document("feed-article/list",
                        requestParameters(
                                parameterWithName("page").description("페이지 넘버")
                        ),
                        responseFields(
                                fieldWithPath("pageNumber").description("페이지 넘버"),
                                fieldWithPath("totalPages").description("전체 페이지 수"),
                                fieldWithPath("articles[].title").description("피드 내 게시글 제목"),
                                fieldWithPath("articles[].link").description("피드 내 게시글 링크"),
                                fieldWithPath("articles[].publishedDate").description("피드 내 게시글 발행 날짜"),
                                fieldWithPath("articles[].feedSourceDto.id").description("Feed Source 아이디"),
                                fieldWithPath("articles[].feedSourceDto.sourceUrl").description("Feed Source URL"),
                                fieldWithPath("articles[].feedSourceDto.description").description("Feed Source 설명")
                        )
                ))
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
        String cookie = loginWithCoach();
        String requestSearchType = "feedDescription";
        String requestSearchContent = "TEST";
        FeedArticleResponseDtos result = searchFeedArticles(cookie, requestSearchType, requestSearchContent);

        List<FeedArticleResponseDto> feedArticles = result.getArticles();
        assertTrue(feedArticles.size() != 0);
        for (FeedArticleResponseDto feedArticle : feedArticles) {
            System.out.println(feedArticle.getFeedSourceDto().getDescription());
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
                .consumeWith(document("feed-article/search",
                        requestParameters(
                                parameterWithName("type").description("검색 조건"),
                                parameterWithName("content").description("검색 내용"),
                                parameterWithName("page").description("페이지 넘버")
                        ),
                        responseFields(
                                fieldWithPath("pageNumber").description("페이지 넘버"),
                                fieldWithPath("totalPages").description("전체 페이지 수"),
                                fieldWithPath("articles[].title").description("피드 내 게시글 제목"),
                                fieldWithPath("articles[].link").description("피드 내 게시글 링크"),
                                fieldWithPath("articles[].publishedDate").description("피드 내 게시글 발행 날짜"),
                                fieldWithPath("articles[].feedSourceDto.id").description("Feed Source 아이디"),
                                fieldWithPath("articles[].feedSourceDto.sourceUrl").description("Feed Source URL"),
                                fieldWithPath("articles[].feedSourceDto.description").description("Feed Source 설명")
                        )
                ))
                .returnResult()
                .getResponseBody();
    }
}
