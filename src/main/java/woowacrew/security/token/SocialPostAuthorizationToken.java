package woowacrew.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import woowacrew.user.domain.User;

import java.util.Arrays;
import java.util.Collection;

public class SocialPostAuthorizationToken extends UsernamePasswordAuthenticationToken {
    public SocialPostAuthorizationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public SocialPostAuthorizationToken(User user) {
        super(user, user, parseAuthorities(user.getUserId()));
    }

    private static Collection<? extends GrantedAuthority> parseAuthorities(String role) {
        //Todo 추후 UserContext의 Role이 담길 예정
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
