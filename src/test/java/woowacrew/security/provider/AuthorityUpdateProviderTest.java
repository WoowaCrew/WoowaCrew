package woowacrew.security.provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import woowacrew.degree.domain.Degree;
import woowacrew.security.token.AuthorityUpdateToken;
import woowacrew.security.token.SocialPostAuthorizationToken;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserContext;
import woowacrew.user.service.UserInternalService;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorityUpdateProviderTest {
    @Mock
    private UserInternalService userInternalService;

    @InjectMocks
    private AuthorityUpdateProvider provider;

    private AuthorityUpdateToken token;

    @BeforeEach
    void setUp() {
        UserContext userContext = new UserContext(1L, "oauthId", "van", UserRole.ROLE_CREW);
        token = new AuthorityUpdateToken(userContext);
    }

    @Test
    void supports_테스트() {
        assertThat(provider.supports(token.getClass())).isTrue();
    }

    @Test
    void 유저_정보를_업데이트한_SocialPostAuthenticationFilter를_리턴하는지_테스트() {
        User user = new User("oauthId", UserRole.ROLE_ADMIN, new Degree());

        when(userInternalService.findById(1L)).thenReturn(user);

        Authentication authenticate = provider.authenticate(token);

        assertThat(authenticate).isInstanceOf(SocialPostAuthorizationToken.class);
        assertThat(authenticate.getAuthorities()).isEqualTo(Collections.singletonList(new SimpleGrantedAuthority(UserRole.ROLE_ADMIN.toString())));
    }
}