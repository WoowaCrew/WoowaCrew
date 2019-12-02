package woowacrew.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import woowacrew.security.filter.SocialLoginFilter;
import woowacrew.security.provider.SocialLoginAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final SocialLoginAuthenticationProvider socialLoginAuthenticationProvider;

    public SecurityConfig(SocialLoginAuthenticationProvider socialLoginAuthenticationProvider) {
        this.socialLoginAuthenticationProvider = socialLoginAuthenticationProvider;
    }

    protected SocialLoginFilter socialLoginFilter() throws Exception {
        SocialLoginFilter filter = new SocialLoginFilter("/oauth/github");
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //정적 페이지는 무시한다.
        web.ignoring()
                .mvcMatchers("/css/**", "/image/**", "/js/**", "/favicon.ico");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(socialLoginAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .and()
                .cors().disable();
        http
                .authorizeRequests()
                .antMatchers("/", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .exceptionHandling().accessDeniedPage("/accessdeny");
        http
                .addFilterBefore(socialLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
