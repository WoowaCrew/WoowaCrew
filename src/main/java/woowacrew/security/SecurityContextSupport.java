package woowacrew.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import woowacrew.security.exception.NotLoginException;
import woowacrew.security.token.SocialPostAuthorizationToken;
import woowacrew.user.dto.UserContext;

import java.util.Objects;

public class SecurityContextSupport {
    public static void updateContext(UserContext userContext) {
        SocialPostAuthorizationToken token = new SocialPostAuthorizationToken(userContext);

        getSecurityContext().setAuthentication(token);
    }

    public static boolean isNotLogined() {
        Authentication authentication = getSecurityContext().getAuthentication();
        return Objects.isNull(authentication) || (authentication instanceof AnonymousAuthenticationToken);
    }

    public static UserContext getUserContext() {
        if (isNotLogined()) {
            throw new NotLoginException();
        }
        Authentication authentication = getSecurityContext().getAuthentication();
        return (UserContext) authentication.getPrincipal();
    }

    private static SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }
}
