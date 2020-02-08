package woowacrew.article.slack.controller;

import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import woowacrew.article.slack.TestSlackConfig;
import woowacrew.article.slack.dto.SlackMessageResponseDto;
import woowacrew.common.controller.CommonTestController;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@TestPropertySource(properties = "spring.config.location=../WoowaCrew/src/test/resources/slack.yml")
class SlackMessageApiControllerTest extends CommonTestController {

    @Autowired
    private TestSlackConfig slackConfig;

    private JSONObject requestSlackMessageFromBot(String channelId, String authorId) throws JSONException {
        JSONObject result = requestSlackMessage(channelId, authorId);
        JSONObject event = (JSONObject) result.get("event");

        event.put("bot_profile", "I am bot");
        result.put("event", event);
        return result;
    }

    private JSONObject requestSlackMessage(String channelId, String authorId) throws JSONException {
        JSONObject result = new JSONObject();

        JSONObject event = new JSONObject();
        event.put("text", "good");
        event.put("user", authorId);
        event.put("channel", channelId);

        result.put("event", event);
        return result;
    }

    private JSONObject requestSlackMessageWithFile(String channelId, String authorId) throws JSONException {
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
    @DisplayName("정상적으로 슬랙 메시지를 저장한다.")
    void createSlackMessage() throws JSONException {
        String channelId = slackConfig.getChannelId();
        String authorId = slackConfig.getAuthorId();

        JSONObject request = requestSlackMessage(channelId, authorId);
        webTestClient.post()
                .uri("/api/slack")
                .body(Mono.just(request.toString()), String.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("정상적으로 파일이 포함된 슬랙 메시지를 저장한다.")
    void createSlackMessageWithFile() throws JSONException {
        String channelId = slackConfig.getChannelId();
        String authorId = slackConfig.getAuthorId();

        JSONObject request = requestSlackMessageWithFile(channelId, authorId);
        webTestClient.post()
                .uri("/api/slack")
                .body(Mono.just(request.toString()), String.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("slack-api/create",
                        requestFields(
                                fieldWithPath("event.channel").type(JsonFieldType.STRING).description("채널 ID"),
                                fieldWithPath("event.user").type(JsonFieldType.STRING).description("작성자 ID"),
                                fieldWithPath("event.text").type(JsonFieldType.STRING).description("메세지 내용"),
                                fieldWithPath("event.files.url_private_download").type(JsonFieldType.STRING).description("다운로드 링크"),
                                fieldWithPath("event.files.permalink").type(JsonFieldType.STRING).description("슬랙으로 이동해서 다운받는 링크")
                        )
                ));
    }

    @Test
    @DisplayName("올바르지 않은 채널 Id로 인해 슬랙 메세지를 저장하는데 실패한다.")
    void createSlackMessageFailBecauseChannelId() throws JSONException {
        String channelId = "invalid channel id";
        String authorId = slackConfig.getAuthorId();

        JSONObject request = requestSlackMessageWithFile(channelId, authorId);
        webTestClient.post()
                .uri("/api/slack")
                .body(Mono.just(request.toString()), String.class)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("올바르지 않은 작성 Id로 인해 슬랙 메세지를 저장하는데 실패한다.")
    void createSlackMessageFailBecauseAuthorId() throws JSONException {
        String channelId = slackConfig.getChannelId();
        String authorId = "invalid author id";

        JSONObject request = requestSlackMessageWithFile(channelId, authorId);
        webTestClient.post()
                .uri("/api/slack")
                .body(Mono.just(request.toString()), String.class)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("봇이 메세지를 보내는 경우 슬랙 메세지를 저장하는데 실패한다.")
    void createSlackMessageFailBecauseBot() throws JSONException {
        String channelId = slackConfig.getChannelId();
        String authorId = slackConfig.getAuthorId();

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