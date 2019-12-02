package woowacrew.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import woowacrew.security.token.SocialPostAuthorizationToken;
import woowacrew.security.token.SocialPreAuthorizationToken;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRepository;

import java.util.Arrays;
import java.util.Optional;

@Component
public class TestLoginProvider implements AuthenticationProvider {
    private final UserRepository userRepository;

    public TestLoginProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = Optional.ofNullable(userRepository.findByUserId("userId"))
                .orElseGet(() -> userRepository.save(new User("userId", "url")));
        return new SocialPostAuthorizationToken(user, user, Arrays.asList(new SimpleGrantedAuthority((String) authentication.getPrincipal())));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SocialPreAuthorizationToken.class.isAssignableFrom(authentication);
    }
}
