package woowacrew.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;
import woowacrew.security.exception.NotLoginException;
import woowacrew.security.token.SocialPostAuthorizationToken;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SecurityContextSupportTest {
    @Test
    void 로그인되어있다면_false를_리턴() {
        UserContext userContext = new UserContext(1L, "oauthId", "nickname", UserRole.ROLE_PRECOURSE);
        SocialPostAuthorizationToken token = new SocialPostAuthorizationToken(userContext);
        SecurityContextHolder.getContext().setAuthentication(token);

        assertThat(SecurityContextSupport.isNotLogined()).isFalse();
        SecurityContextHolder.clearContext();
    }

    @Test
    void 로그인되어있지_않다면_true를_리턴() {
        assertThat(SecurityContextSupport.isNotLogined()).isTrue();
    }

    @Test
    void 로그인이되어있다면_userContext를_리턴() {
        UserContext userContext = new UserContext(1L, "oauthId", "nickname", UserRole.ROLE_PRECOURSE);
        SocialPostAuthorizationToken token = new SocialPostAuthorizationToken(userContext);
        SecurityContextHolder.getContext().setAuthentication(token);

        assertThat(SecurityContextSupport.getUserContext()).isEqualTo(userContext);
        SecurityContextHolder.clearContext();
    }

    @Test
    void 로그인이_되어있지_않은채로_userContext를_얻으려하면_exception_발생() {
        assertThrows(NotLoginException.class, SecurityContextSupport::getUserContext);
    }
}