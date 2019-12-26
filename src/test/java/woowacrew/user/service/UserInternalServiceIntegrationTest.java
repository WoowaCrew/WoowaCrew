package woowacrew.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserInternalServiceIntegrationTest {
    @Autowired
    private UserInternalService userInternalService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void findById_캐싱_테스트() {
        assertThat(cacheManager.getCache("user").get(1L)).isNull();
        userInternalService.findById(1L);
        assertThat(cacheManager.getCache("user").get(1L)).isNotNull();
    }
}
