package woowacrew.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import woowacrew.oauth.OauthService;
import woowacrew.security.token.SocialPostAuthorizationToken;
import woowacrew.security.token.SocialPreAuthorizationToken;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserOauthDto;
import woowacrew.user.domain.UserRepository;

@Component
public class SocialLoginAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final OauthService oauthService;

    public SocialLoginAuthenticationProvider(UserRepository userRepository, OauthService oauthService) {
        this.userRepository = userRepository;
        this.oauthService = oauthService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SocialPreAuthorizationToken token = (SocialPreAuthorizationToken) authentication;
        String code = token.getCode();
        String accessToken = oauthService.getAccessToken(code);
        UserOauthDto userDto = oauthService.getUserInfo(accessToken);
        User user = userRepository.findByUserId(userDto.getUserId())
                .orElseGet(() -> registerUser(userDto));
        //Todo UserContext를 만들어서 리턴해주자. UserContext의 정보는 User Entity의 컬럼이 나오면 적용
        return new SocialPostAuthorizationToken(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SocialPreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private User registerUser(UserOauthDto userDto) {
        //Todo User 엔티티의 createUser() 사용 예정
        return userRepository.save(new User(userDto.getUserId(), null));
    }
}
