package woowacrew.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserApproveDto;
import woowacrew.user.dto.UserContext;
import woowacrew.user.utils.UserConverter;

import java.time.LocalDate;

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

    @Test
    void findById로_캐시가_저장된_뒤_정보가_업데이트_된다면_캐시를_지운다() {
        User user = userInternalService.findById(1L);
        assertThat(cacheManager.getCache("user").get(1L)).isNotNull();

        userInternalService.update(user.getId(), "van", LocalDate.now());
        assertThat(cacheManager.getCache("user").get(1L)).isNull();

        User updatedUser = userInternalService.findById(1L);
        assertThat(cacheManager.getCache("user").get(1L)).isNotNull();
        UserContext userContext = UserConverter.userToUserContextDto(updatedUser);
        UserApproveDto approveDto = new UserApproveDto(UserRole.ROLE_ADMIN, 1);
        userInternalService.approveUserFor(updatedUser.getId(), userContext, approveDto);
        assertThat(cacheManager.getCache("user").get(1L)).isNull();
    }
}
