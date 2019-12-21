package woowacrew.security.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import woowacrew.security.requestmatcher.AuthorityUpdateRequestMatcher;
import woowacrew.security.token.AuthorityUpdateToken;
import woowacrew.security.token.SocialPostAuthorizationToken;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserContext;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorityUpdateFilterTest {
    private AuthorityUpdateFilter filter;

    @BeforeEach
    void setUp() {
        List<String> skipPaths = Arrays.asList("/", "/login", "/login/**", "/search", "/search/**", "/docs/**");
        AuthorityUpdateRequestMatcher requestMatcher = new AuthorityUpdateRequestMatcher(skipPaths);
        filter = new AuthorityUpdateFilter(requestMatcher);

        UserContext userContext = new UserContext(1L, "oauthId", "van", UserRole.ROLE_CREW);
        SocialPostAuthorizationToken authorityUpdateToken = new SocialPostAuthorizationToken(userContext);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        securityContext.setAuthentication(authorityUpdateToken);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void AuthorityUpdateToken을_잘_리턴하는지_테스트() {
        Authentication authentication = filter.attemptAuthentication(new MockHttpServletRequest(), new MockHttpServletResponse());

        assertThat(authentication).isInstanceOf(AuthorityUpdateToken.class);
    }
}