package woowacrew.article.slack.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "slack")
public class SlackConfig {
    private String token;
    private String channelId;

    public String getToken() {
        return token;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
