package woowacrew.oauth;

import woowacrew.user.dto.UserOauthDto;

public interface OauthService {
    String getAccessToken(String accessCode);

    UserOauthDto getUserInfo(String accessToken);

    String getAuthorizationUrl();
}
