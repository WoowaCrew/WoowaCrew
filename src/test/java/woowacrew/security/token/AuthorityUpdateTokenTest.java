package woowacrew.security.token;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserContext;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorityUpdateTokenTest {
    @Test
    void 생성_테스트() {
        UserRole role = UserRole.ROLE_CREW;
        UserContext userContext = new UserContext(1L, "oauthId", "van", role);
        AuthorityUpdateToken token = new AuthorityUpdateToken(userContext);
        
        assertThat((UserContext) token.getPrincipal()).isEqualTo(userContext);
        assertThat(token.getAuthorities()).isEqualTo(Collections.singletonList(new SimpleGrantedAuthority(role.toString())));
    }
}