package woowacrew.article.slack.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "slack")
public class SlackConfig {
    private String token;
    private String noticeChannelId;

    public String getToken() {
        return token;
    }

    public String getNoticeChannelId() {
        return noticeChannelId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setNoticeChannelId(String noticeChannelId) {
        this.noticeChannelId = noticeChannelId;
    }
}
