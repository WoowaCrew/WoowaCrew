package woowacrew.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import woowacrew.security.token.AuthorityUpdateToken;
import woowacrew.security.token.SocialPostAuthorizationToken;
import woowacrew.user.domain.User;
import woowacrew.user.dto.UserContext;
import woowacrew.user.service.UserInternalService;
import woowacrew.user.utils.UserConverter;

@Component
public class AuthorityUpdateProvider implements AuthenticationProvider {
    private final UserInternalService userInternalService;

    public AuthorityUpdateProvider(UserInternalService userInternalService) {
        this.userInternalService = userInternalService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserContext userContext = (UserContext) authentication.getPrincipal();

        User user = userInternalService.findById(userContext.getId());
        UserContext updatedUserContext = UserConverter.toContextDto(user);

        return new SocialPostAuthorizationToken(updatedUserContext);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthorityUpdateToken.class.isAssignableFrom(authentication);
    }
}
