package woowacrew.security.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import woowacrew.security.token.AuthorityUpdateToken;
import woowacrew.user.dto.UserContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorityUpdateFilter extends AbstractAuthenticationProcessingFilter {
    protected AuthorityUpdateFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    // TODO: 2019-12-21 여기서 SecurityContextHolder에 담긴 UserContext를 Token에 담는다.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserContext userContext = (UserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new AuthorityUpdateToken(userContext);
    }

    // TODO: 2019-12-21 성공한다면 UserContext를 업데이트
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }
}
