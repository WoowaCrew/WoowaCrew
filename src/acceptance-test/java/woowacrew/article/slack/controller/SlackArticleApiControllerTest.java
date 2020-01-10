package woowacrew.article.slack.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacrew.article.slack.dto.SlackMessageResponseDtos;
import woowacrew.common.controller.CommonTestController;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}