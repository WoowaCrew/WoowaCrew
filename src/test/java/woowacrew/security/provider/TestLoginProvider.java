package woowacrew.security.provider;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import woowacrew.security.token.SocialPostAuthorizationToken;
import woowacrew.security.token.SocialPreAuthorizationToken;
import woowacrew.user.domain.*;

import java.util.Arrays;

@Component
public class TestLoginProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final DegreeRepository degreeRepository;

    public TestLoginProvider(UserRepository userRepository, DegreeRepository degreeRepository) {
        this.userRepository = userRepository;
        this.degreeRepository = degreeRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Degree degree = degreeRepository.findByNumber(0)
                .orElseThrow(IllegalArgumentException::new);
        String oauthId = (String) authentication.getCredentials();
        String role = (String) authentication.getPrincipal();

        UserRole userRole = Arrays.stream(UserRole.values()).filter(value -> value.toString().equals(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("권한 못찾음"));
        User user = userRepository.findByOauthId(oauthId)
                .orElseGet(() -> userRepository.save(new User(oauthId, userRole, degree)));

        UserContext userContext = new ModelMapper().map(user, UserContext.class);
        return new SocialPostAuthorizationToken(userContext, userContext, Arrays.asList(new SimpleGrantedAuthority(userRole.toString())));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SocialPreAuthorizationToken.class.isAssignableFrom(authentication);
    }
}
