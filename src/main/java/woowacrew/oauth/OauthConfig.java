package woowacrew.oauth;

public interface OauthConfig {
    String getClientId();

    String getClientSecret();

    String getUserAuthorizationUri();

    String getAccessTokenUri();

    String getUserInfoUri();
}
