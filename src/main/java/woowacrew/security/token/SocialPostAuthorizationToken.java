package woowacrew.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserContext;

import java.util.Arrays;
import java.util.Collection;

public class SocialPostAuthorizationToken extends UsernamePasswordAuthenticationToken {
    public SocialPostAuthorizationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public SocialPostAuthorizationToken(UserContext userContext) {
        super(userContext, userContext, parseAuthorities(userContext.getRole()));
    }

    private static Collection<? extends GrantedAuthority> parseAuthorities(UserRole role) {
        return Arrays.asList(new SimpleGrantedAuthority(role.toString()));
    }
}
