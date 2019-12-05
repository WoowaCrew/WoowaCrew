package woowacrew.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import woowacrew.security.filter.SocialLoginTestFilter;
import woowacrew.security.handler.AccessDenyHandler;
import woowacrew.security.provider.TestLoginProvider;
import woowacrew.user.domain.UserRole;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TestLoginProvider testLoginProvider;

    public SecurityConfig(TestLoginProvider testLoginProvider) {
        this.testLoginProvider = testLoginProvider;
    }

    protected SocialLoginTestFilter socialLoginFilter() throws Exception {
        SocialLoginTestFilter filter = new SocialLoginTestFilter("/oauth/github");
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
                .authenticationProvider(testLoginProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable();
        http
                .authorizeRequests()
                .antMatchers("/", "/login", "/search", "/search/**").permitAll()
                .antMatchers("/accessdeny", "/users/form", "/users/update").authenticated()
                .anyRequest().hasAnyRole(UserRole.ROLE_CREW.getRoleName(), UserRole.ROLE_COACH.getRoleName(), UserRole.ROLE_ADMIN.getRoleName())
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .exceptionHandling().accessDeniedHandler(new AccessDenyHandler())
                .and()
                .logout()
                .logoutSuccessUrl("/");
        http
                .addFilterBefore(socialLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
