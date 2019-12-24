package woowacrew.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import woowacrew.security.filter.SocialLoginFilter;
import woowacrew.security.provider.AuthorityUpdateProvider;
import woowacrew.security.provider.SocialLoginAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends AbstractSecurityConfig {
    private final SocialLoginAuthenticationProvider socialLoginAuthenticationProvider;
    private final AuthorityUpdateProvider authorityUpdateProvider;

    public SecurityConfig(SocialLoginAuthenticationProvider socialLoginAuthenticationProvider, AuthorityUpdateProvider authorityUpdateProvider) {
        this.socialLoginAuthenticationProvider = socialLoginAuthenticationProvider;
        this.authorityUpdateProvider = authorityUpdateProvider;
    }

    @Override
    protected SocialLoginFilter socialLoginFilter() throws Exception {
        SocialLoginFilter filter = new SocialLoginFilter("/oauth/github");
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(socialLoginAuthenticationProvider)
                .authenticationProvider(authorityUpdateProvider);
    }
}
