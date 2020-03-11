package woowacrew;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import woowacrew.degree.service.DegreeInternalService;
import woowacrew.user.service.UserInternalService;

@Configuration
@ComponentScan(basePackageClasses = {
        UserInternalService.class,
        DegreeInternalService.class,
})
@EntityScan(basePackages = {
        "woowacrew"
})
@EnableJpaRepositories(basePackages = {
        "woowacrew"
})
@EnableCaching
public class JpaTestConfiguration {
    @Bean
    public CacheManager cacheManager() {
        return new CaffeineCacheManager();
    }
}


