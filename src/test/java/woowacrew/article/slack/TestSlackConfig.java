package woowacrew.article.slack;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:slack.yml")
@ConfigurationProperties(prefix = "slack")
public class TestSlackConfig {
    private String token;
    private String testChannelId;
    private String authorId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTestChannelId() {
        return testChannelId;
    }

    public void setTestChannelId(String testChannelId) {
        this.testChannelId = testChannelId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
