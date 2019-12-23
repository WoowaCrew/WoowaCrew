package woowacrew.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import woowacrew.security.filter.SocialLoginFilter;
import woowacrew.security.filter.SocialLoginTestFilter;
import woowacrew.security.provider.AuthorityUpdateProvider;
import woowacrew.security.provider.TestLoginProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends AbstractSecurityConfig {
    private final TestLoginProvider testLoginProvider;
    private final AuthorityUpdateProvider authorityUpdateProvider;

    public SecurityConfig(TestLoginProvider testLoginProvider, AuthorityUpdateProvider authorityUpdateProvider) {
        this.testLoginProvider = testLoginProvider;
        this.authorityUpdateProvider = authorityUpdateProvider;
    }

    @Override
    protected SocialLoginFilter socialLoginFilter() throws Exception {
        SocialLoginTestFilter filter = new SocialLoginTestFilter("/oauth/github");
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(testLoginProvider)
                .authenticationProvider(authorityUpdateProvider);
    }
}
