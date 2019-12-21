package woowacrew.security.requestmatcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorityUpdateRequestMatcherTest {
    private AuthorityUpdateRequestMatcher authorityUpdateRequestMatcher;

    @BeforeEach
    void setUp() {
        List<String> skipPaths = Arrays.asList("/", "/login", "/login/**", "/search", "/search/**", "/docs/**");
        authorityUpdateRequestMatcher = new AuthorityUpdateRequestMatcher(skipPaths);
    }

    @Test
    void skipPath라면_false를_리턴() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/login");
        request.setServletPath("/login");
        request.setMethod("GET");

        assertThat(authorityUpdateRequestMatcher.matches(request)).isFalse();
    }

    @Test
    void skipPath가_아니라면_true를_리턴() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/articles/crew");
        request.setServletPath("/articles/crew");
        request.setMethod("GET");

        assertThat(authorityUpdateRequestMatcher.matches(request)).isTrue();
    }
}
