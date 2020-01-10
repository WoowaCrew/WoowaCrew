package woowacrew.article.slack.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacrew.article.slack.dto.SlackMessageResponseDto;
import woowacrew.article.slack.dto.SlackMessageResponseDtos;
import woowacrew.common.controller.CommonTestController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

class SlackArticleApiControllerTest extends CommonTestController {
    @Test
    @DisplayName("정상적으로 슬랙 메세지들을 가져온다.")
    void list() {
        String cookie = loginWithCrew();

        SlackMessageResponseDtos result = webTestClient.get()
                .uri("/api/articles/slack")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody(SlackMessageResponseDtos.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(result);
        assertTrue(result.getSlackMessages().size() != 0);
    }

    @Test
    @DisplayName("로그인 하지 않으면 슬랙 메세지들을 가져오는데 실패한다.")
    void listFail() {
        webTestClient.get()
                .uri("/api/articles/slack")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/login"));
    }

    @Test
    @DisplayName("정상적으로 Id로 슬랙 메세지를 가져온다.")
    void findById() {
        String cookie = loginWithCrew();

        SlackMessageResponseDto result = webTestClient.get()
                .uri("/api/articles/slack/{id}", 1)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody(SlackMessageResponseDto.class)
                .consumeWith(document("slack-api/read",
                        pathParameters(
                                parameterWithName("id").description("슬랙 메세지 ID")),
                        responseFields(
                                fieldWithPath("id").description("슬랙 메세지 ID"),
                                fieldWithPath("channel").description("채널 이름"),
                                fieldWithPath("author").description("작성자 이름"),
                                fieldWithPath("content").description("메시지 내용"),
                                fieldWithPath("downloadLink").description("파일 다운로드 링크"),
                                fieldWithPath("downloadLinkFromSlack").description("슬랙에서 파일 다운로드 하는 링크"))
                ))
                .returnResult()
                .getResponseBody();

        assertNotNull(result);
        assertThat(result.getContent()).contains("test");
    }

    @Test
    @DisplayName("없는 Id로 슬랙 메세지를 가져오는데 실패한다.")
    void findByNotExistId() {
        String cookie = loginWithCrew();

        webTestClient.get()
                .uri("/api/articles/slack/{id}", 0)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("로그인 하지 않으면 Id로 슬랙 메세지를 가져오는데 실패한다.")
    void findByIdFail() {
        webTestClient.get()
                .uri("/api/articles/slack/{id}", 1)
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/login"));
    }
}