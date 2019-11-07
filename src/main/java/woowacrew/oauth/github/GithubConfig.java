package woowacrew.oauth.github;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import woowacrew.oauth.OauthConfig;

@Configuration
@ConfigurationProperties(prefix = "github.client")
public class GithubConfig implements OauthConfig {
    private String clientId;
    private String clientSecret;
    private String userAuthorizationUri;
    private String accessTokenUri;
    private String userInfoUri;

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public String getUserAuthorizationUri() {
        return userAuthorizationUri;
    }

    @Override
    public String getAccessTokenUri() {
        return accessTokenUri;
    }

    @Override
    public String getUserInfoUri() {
        return userInfoUri;
    }

    public void setUserAuthorizationUri(String userAuthorizationUri) {
        this.userAuthorizationUri = userAuthorizationUri;
    }


    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
    }


    public void setUserInfoUri(String userInfoUri) {
        this.userInfoUri = userInfoUri;
    }
}
