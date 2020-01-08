package woowacrew.article.slack.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import woowacrew.article.slack.TestSlackConfig;

@SpringBootTest(properties = "spring.config.location=classpath:/slack.yml",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SlackMessageApiControllerTest {

    @Autowired
    private TestSlackConfig slackConfig;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("정상적으로 슬랙 메시지를 저장한다.")
    void createSlackMessage() throws JSONException {
        String channelId = slackConfig.getChannelId();
        String authorId = slackConfig.getAuthorId();

        JSONObject request = requestSlackMessageWithFile(channelId, authorId);
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
                .expectStatus().isOk();
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
}