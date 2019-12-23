package woowacrew.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import woowacrew.security.token.AuthorityUpdateToken;
import woowacrew.security.token.SocialPostAuthorizationToken;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRepository;
import woowacrew.user.dto.UserContext;
import woowacrew.user.service.exception.NotExistUserException;
import woowacrew.user.utils.UserConverter;

@Component
public class AuthorityUpdateProvider implements AuthenticationProvider {
    private final UserRepository userRepository;

    public AuthorityUpdateProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserContext userContext = (UserContext) authentication.getPrincipal();
        User user = userRepository.findById(userContext.getId())
                .orElseThrow(NotExistUserException::new);
        UserContext updatedUserContext = UserConverter.userToUserContextDto(user);
        return new SocialPostAuthorizationToken(updatedUserContext);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthorityUpdateToken.class.isAssignableFrom(authentication);
    }
}
