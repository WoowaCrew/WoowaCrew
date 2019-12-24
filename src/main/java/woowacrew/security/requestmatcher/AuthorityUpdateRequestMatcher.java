package woowacrew.security.requestmatcher;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class AuthorityUpdateRequestMatcher implements RequestMatcher {
    @Override
    public boolean matches(HttpServletRequest request) {
        return Objects.nonNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
