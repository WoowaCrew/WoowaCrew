package woowacrew.utils.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class PageConfiguration {
    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer pageableHandlerMethodArgumentResolverCustomizer() {
        return pageableResolver -> pageableResolver.setOneIndexedParameters(true);
    }
}
