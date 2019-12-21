package woowacrew.security.requestmatcher;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import woowacrew.security.token.SocialPostAuthorizationToken;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserContext;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorityUpdateRequestMatcherTest {
    private AuthorityUpdateRequestMatcher authorityUpdateRequestMatcher = new AuthorityUpdateRequestMatcher();

    @Test
    void SeucrityConext가_없다면_false를_리턴() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        assertThat(authorityUpdateRequestMatcher.matches(request)).isFalse();
    }

    @Test
    void SeucrityConext가_있다면_true를_리턴() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        UserContext userContext = new UserContext(1L, "oauthId", "van", UserRole.ROLE_CREW);
        SocialPostAuthorizationToken token = new SocialPostAuthorizationToken(userContext);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(token);
        SecurityContextHolder.setContext(securityContext);
        assertThat(authorityUpdateRequestMatcher.matches(request)).isTrue();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }
}
