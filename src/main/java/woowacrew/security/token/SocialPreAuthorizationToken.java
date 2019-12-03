package woowacrew.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SocialPreAuthorizationToken extends UsernamePasswordAuthenticationToken {
    public SocialPreAuthorizationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public String getCode() {
        return (String) getCredentials();
    }
}
