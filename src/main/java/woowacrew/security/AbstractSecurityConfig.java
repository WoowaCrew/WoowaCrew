package woowacrew.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import woowacrew.security.filter.SocialLoginFilter;
import woowacrew.security.handler.AccessDenyHandler;
import woowacrew.user.domain.UserRole;

public abstract class AbstractSecurityConfig extends WebSecurityConfigurerAdapter {

    protected abstract SocialLoginFilter socialLoginFilter() throws Exception;

    @Override
    public void configure(WebSecurity web) throws Exception {
        //정적 페이지는 무시한다.
        web.ignoring()
                .mvcMatchers("/css/**", "/image/**", "/js/**", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable();
        http
                .authorizeRequests()
                .antMatchers("/", "/login", "/login/**", "/search", "/search/**").permitAll()
                //TODO: 임시권한입니다. 추후 삭제 필요
                .antMatchers("/role/**").permitAll()
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
