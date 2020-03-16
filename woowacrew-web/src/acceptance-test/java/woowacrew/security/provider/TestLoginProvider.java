package woowacrew.security.provider;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import woowacrew.common.service.FieldSetter;
import woowacrew.degree.domain.Degree;
import woowacrew.degree.domain.DegreeRepository;
import woowacrew.oauth.OauthService;
import woowacrew.security.token.SocialPostAuthorizationToken;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRepository;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserContext;

import java.util.Arrays;

@Component
public class TestLoginProvider extends SocialLoginAuthenticationProvider {
    private final UserRepository userRepository;
    private final DegreeRepository degreeRepository;
    private final OauthService oauthService;

    public TestLoginProvider(UserRepository userRepository, DegreeRepository degreeRepository, OauthService oauthService) {
        super(userRepository, degreeRepository, oauthService);
        this.userRepository = userRepository;
        this.degreeRepository = degreeRepository;
        this.oauthService = oauthService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Degree degree = degreeRepository.findByDegreeNumber(Degree.NONE_DEGREE)
                .orElseThrow(IllegalArgumentException::new);
        String oauthId = (String) authentication.getCredentials();
        String role = (String) authentication.getPrincipal();

        UserRole userRole = Arrays.stream(UserRole.values()).filter(value -> value.toString().equals(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("권한 못찾음"));
        User user = userRepository.findByOauthId(oauthId)
                .orElseGet(() -> userRepository.save(new User(oauthId, userRole, degree)));
        FieldSetter.set(user, "role", userRole);

        UserContext userContext = new ModelMapper().map(user, UserContext.class);
        return new SocialPostAuthorizationToken(userContext, userContext, Arrays.asList(new SimpleGrantedAuthority(userRole.toString())));
    }
}
