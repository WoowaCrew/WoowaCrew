package woowacrew.oauth;

import woowacrew.user.domain.UserDto;

public interface Oauth {
    String getAccessToken(String accessCode);

    UserDto getUserInfo(String accessToken);
}
