package woowacrew.security.provider;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import woowacrew.degree.domain.Degree;
import woowacrew.degree.domain.DegreeRepository;
import woowacrew.oauth.OauthService;
import woowacrew.security.token.SocialPostAuthorizationToken;
import woowacrew.security.token.SocialPreAuthorizationToken;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRepository;
import woowacrew.user.domain.exception.DegreeBoundException;
import woowacrew.user.dto.UserContext;
import woowacrew.user.dto.UserOauthDto;

@Component
public class SocialLoginAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final DegreeRepository degreeRepository;
    private final OauthService oauthService;

    public SocialLoginAuthenticationProvider(UserRepository userRepository, DegreeRepository degreeRepository, OauthService oauthService) {
        this.userRepository = userRepository;
        this.degreeRepository = degreeRepository;
        this.oauthService = oauthService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SocialPreAuthorizationToken token = (SocialPreAuthorizationToken) authentication;
        String code = token.getCode();
        String accessToken = oauthService.getAccessToken(code);
        UserOauthDto userOauthDto = oauthService.getUserInfo(accessToken);
        User user = userRepository.findByOauthId(userOauthDto.getOauthId())
                .orElseGet(() -> registerUser(userOauthDto));
        UserContext userContext = new ModelMapper().map(user, UserContext.class);
        return new SocialPostAuthorizationToken(userContext);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SocialPreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private User registerUser(UserOauthDto userOauthDto) {
        Degree degree = degreeRepository.findByDegreeNumber(0)
                .orElseThrow(DegreeBoundException::new);
        return userRepository.save(new User(userOauthDto.getOauthId(), userOauthDto.getGithubId(), degree));
    }
}
