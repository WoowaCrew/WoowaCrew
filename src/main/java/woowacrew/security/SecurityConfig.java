package woowacrew.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import woowacrew.security.filter.SocialLoginFilter;
import woowacrew.security.provider.SocialLoginAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends AbstractSecurityConfig {
    private final SocialLoginAuthenticationProvider socialLoginAuthenticationProvider;

    public SecurityConfig(SocialLoginAuthenticationProvider socialLoginAuthenticationProvider) {
        this.socialLoginAuthenticationProvider = socialLoginAuthenticationProvider;
    }

    @Override
    protected SocialLoginFilter socialLoginFilter() throws Exception {
        SocialLoginFilter filter = new SocialLoginFilter("/oauth/github");
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(socialLoginAuthenticationProvider);
    }
}
