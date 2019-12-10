package woowacrew.security;

import org.springframework.security.core.context.SecurityContextHolder;
import woowacrew.security.token.SocialPostAuthorizationToken;
import woowacrew.user.dto.UserContext;

public class SecurityContextSupport {
    public static void updateContext(UserContext userContext) {
        SocialPostAuthorizationToken token = new SocialPostAuthorizationToken(userContext);

        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
