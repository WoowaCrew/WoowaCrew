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
        User user = userRepository.findByOauthId("1234")
                .orElseGet(() -> userRepository.save(new User("userId", degree)));

        UserContext userContext = new ModelMapper().map(user, UserContext.class);
        return new SocialPostAuthorizationToken(userContext, userContext, Arrays.asList(new SimpleGrantedAuthority((String) authentication.getPrincipal())));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SocialPreAuthorizationToken.class.isAssignableFrom(authentication);
    }
}
