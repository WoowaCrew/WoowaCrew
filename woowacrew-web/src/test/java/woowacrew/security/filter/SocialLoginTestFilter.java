package woowacrew.security.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import woowacrew.security.token.SocialPreAuthorizationToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SocialLoginTestFilter extends SocialLoginFilter {
    public SocialLoginTestFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String role = request.getParameter("role");
        String oauthId = request.getParameter("oauthId");

        return super.getAuthenticationManager().authenticate(new SocialPreAuthorizationToken(role, oauthId));
    }
}
