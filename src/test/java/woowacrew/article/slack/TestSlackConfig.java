package woowacrew.article.slack;

public class TestSlackConfig {
    private String token;
    private String testChannelId;
    private String authorId;

    public TestSlackConfig() {
        this.token = "testToken";
        this.testChannelId = "testChannelId";
        this.authorId = "testAuthorId";
    }

    public String getToken() {
        return token;
    }

    public String getTestChannelId() {
        return testChannelId;
    }

    public String getAuthorId() {
        return authorId;
    }
}
