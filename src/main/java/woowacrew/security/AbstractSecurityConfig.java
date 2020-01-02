package woowacrew.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import woowacrew.security.filter.AuthorityUpdateFilter;
import woowacrew.security.filter.SocialLoginFilter;
import woowacrew.security.handler.AccessDenyHandler;
import woowacrew.security.requestmatcher.AuthorityUpdateRequestMatcher;
import woowacrew.user.domain.UserRole;

public abstract class AbstractSecurityConfig extends WebSecurityConfigurerAdapter {

    protected abstract SocialLoginFilter socialLoginFilter() throws Exception;

    private AuthorityUpdateFilter authorityUpdateFilter() throws Exception {
        AuthorityUpdateRequestMatcher requestMatcher = new AuthorityUpdateRequestMatcher();
        AuthorityUpdateFilter filter = new AuthorityUpdateFilter(requestMatcher);
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //정적 페이지는 무시한다.
        web.ignoring()
                .mvcMatchers("/css/**", "/image/**", "/js/**", "/favicon.ico");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors();
        http
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/", "/login", "/login/**", "/search", "/search/**", "/docs/**").permitAll()
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
                .addFilterBefore(socialLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authorityUpdateFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
