package woowacrew.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserContext;

import java.util.Collection;
import java.util.Collections;

public class AuthorityUpdateToken extends UsernamePasswordAuthenticationToken {
    private AuthorityUpdateToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public AuthorityUpdateToken(UserContext userContext) {
        this(userContext, userContext, parseAuthorities(userContext.getRole()));
    }

    private static Collection<? extends GrantedAuthority> parseAuthorities(UserRole role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.toString()));
    }
}
