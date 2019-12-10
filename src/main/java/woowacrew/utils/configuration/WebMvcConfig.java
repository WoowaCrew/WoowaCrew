package woowacrew.utils.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacrew.user.utils.UserRoleConverter;
import woowacrew.utils.resolver.CustomHandlerMethodArgumentResolver;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error").setViewName("error");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers
                .add(new CustomHandlerMethodArgumentResolver());
        resolvers
                .add(pageableHandlerMethodArgumentResolver());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserRoleConverter());
    }

    @Bean
    public PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver() {
        PageableHandlerMethodArgumentResolver pageResolver = new PageableHandlerMethodArgumentResolver();
        pageResolver.setOneIndexedParameters(true);

        return pageResolver;
    }
}