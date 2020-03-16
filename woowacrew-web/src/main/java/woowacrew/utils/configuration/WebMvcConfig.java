package woowacrew.utils.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacrew.user.utils.UserRoleConverter;
import woowacrew.utils.resolver.SearchTypeHandlerMethodArgumentResolver;
import woowacrew.utils.resolver.SlackMessageArgumentResolver;
import woowacrew.utils.resolver.UserContextArgumentResolver;

import java.util.List;

@EnableCaching
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/feeds").setViewName("feed");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserContextArgumentResolver());
        resolvers.add(new SearchTypeHandlerMethodArgumentResolver());
        resolvers.add(new SlackMessageArgumentResolver());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserRoleConverter());
    }
}