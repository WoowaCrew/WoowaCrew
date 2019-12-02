package woowacrew.security.provider;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.oauth.OauthService;
import woowacrew.oauth.github.GithubOauthService;
import woowacrew.security.token.SocialPostAuthorizationToken;
import woowacrew.security.token.SocialPreAuthorizationToken;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserContext;
import woowacrew.user.domain.UserOauthDto;
import woowacrew.user.domain.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SocialLoginAuthenticationProviderTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private OauthService oauthService;

    @InjectMocks
    private SocialLoginAuthenticationProvider socialLoginAuthenticationProvider;

    @Test
    @DisplayName("SocialPreAuthorizationToken은 true가 리턴된다")
    void supportsTest() {
        assertThat(socialLoginAuthenticationProvider.supports(SocialPreAuthorizationToken.class)).isTrue();
    }

    @Test
    @DisplayName("SocialPreAuthorizationToken가 아니면 false가 리턴된다")
    void supportsTest2() {
        assertThat(socialLoginAuthenticationProvider.supports(SocialPostAuthorizationToken.class)).isFalse();
        assertThat(socialLoginAuthenticationProvider.supports(GithubOauthService.class)).isFalse();
    }

    @Test
    @DisplayName("SocialLoginFilter에서 넘어온 code를 가지고 User객체를 가진 SocialPostAuthorizationToken을 리턴해준다.")
    void authenticateTest() {
        String code = "code";
        String accessToken = "accessToken";
        String userId = "woowacrew";
        User user = new User(userId, "url");

        SocialPreAuthorizationToken token = new SocialPreAuthorizationToken(code, code);

        when(oauthService.getAccessToken(code)).thenReturn(accessToken);
        when(oauthService.getUserInfo(accessToken)).thenReturn(new UserOauthDto(userId));
        when(userRepository.findByUserId(userId)).thenReturn(Optional.ofNullable(user));

        SocialPostAuthorizationToken postToken = (SocialPostAuthorizationToken) socialLoginAuthenticationProvider.authenticate(token);
        UserContext exceptedUser = (UserContext) postToken.getPrincipal();
        assertThat(exceptedUser.getUserId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("SocialLoginFilter에서 넘어온 code를 가지고 User객체를 가진 SocialPostAuthorizationToken을 리턴해준다. 유저가 없으면 생성해서 리턴해준다.")
    void authenticateTest2() {
        String code = "code";
        String accessToken = "accessToken";
        String userId = "woowacrew";
        User user = new User(userId, "url");

        SocialPreAuthorizationToken token = new SocialPreAuthorizationToken(code, code);

        when(oauthService.getAccessToken(code)).thenReturn(accessToken);
        when(oauthService.getUserInfo(accessToken)).thenReturn(new UserOauthDto(userId));
        when(userRepository.findByUserId(userId)).thenReturn(Optional.ofNullable(null));
        when(userRepository.save(any())).thenReturn(user);

        SocialPostAuthorizationToken postToken = (SocialPostAuthorizationToken) socialLoginAuthenticationProvider.authenticate(token);
        UserContext exceptedUser = (UserContext) postToken.getPrincipal();
        assertThat(exceptedUser.getUserId()).isEqualTo(userId);
    }
}