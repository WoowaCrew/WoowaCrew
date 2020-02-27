package woowacrew.article.slack.controller;

import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import woowacrew.article.slack.domain.SlackConfig;
import woowacrew.article.slack.dto.SlackMessageResponseDto;
import woowacrew.common.controller.CommonTestController;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SlackMessageApiControllerTest extends CommonTestController {
    private static final String authorId = "UJ0NMF2M9";

    @Autowired
    private SlackConfig slackConfig;

    public static JSONObject requestSlackMessageFromBot(String channelId, String authorId) throws JSONException {
        JSONObject result = requestSlackMessage(channelId, authorId);
        JSONObject event = (JSONObject) result.get("event");

        event.put("bot_profile", "I am bot");
        result.put("event", event);
        return result;
    }

    public static JSONObject requestSlackMessage(String channelId, String authorId) throws JSONException {
        JSONObject result = new JSONObject();

        JSONObject event = new JSONObject();
        event.put("text", "good");
        event.put("user", authorId);
        event.put("channel", channelId);

        result.put("event", event);
        return result;
    }

    public static JSONObject requestSlackMessageWithFile(String channelId, String authorId) throws JSONException {
        JSONObject result = requestSlackMessage(channelId, authorId);
        JSONObject event = (JSONObject) result.get("event");

        JSONObject file = new JSONObject();
        file.put("url_private_download", "test.com");
        file.put("permalink", "test2.com");

        event.put("files", file);
        result.put("event", event);
        return result;
    }

    @Test
    @DisplayName("봇이 메세지를 보내는 경우 슬랙 메세지를 저장하는데 실패한다.")
    void createSlackMessageFailBecauseBot() throws JSONException {
        String channelId = slackConfig.getNoticeChannelId();
        String authorId = this.authorId;

        JSONObject request = requestSlackMessageFromBot(channelId, authorId);
        webTestClient.post()
                .uri("/api/slack")
                .body(Mono.just(request.toString()), String.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("최근 슬랙 메세지를 가져온다.")
    void findRecentlyMessage() {
        String cookie = loginWithCrew();

        SlackMessageResponseDto result = webTestClient.get()
                .uri("/api/slack/notice")
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody(SlackMessageResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(result);
    }

    @Test
    @DisplayName("로그인을 하지 않으면 최근 슬랙 메세지를 가져오는데 실패한다.")
    void findRecentlyMessageFail() {
        webTestClient.get()
                .uri("/api/slack/notice")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .value("Location", Matchers.containsString("/login"));
    }
}