package woowacrew.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import woowacrew.JpaTestConfiguration;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserRole;
import woowacrew.user.dto.UserApproveDto;
import woowacrew.user.dto.UserContext;
import woowacrew.user.utils.UserConverter;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = JpaTestConfiguration.class)
@ActiveProfiles("test")
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
        UserContext userContext = UserConverter.toContextDto(updatedUser);
        UserApproveDto approveDto = new UserApproveDto(UserRole.ROLE_ADMIN, 1);
        userInternalService.approveUserFor(updatedUser.getId(), userContext, approveDto);
        assertThat(cacheManager.getCache("user").get(1L)).isNull();
    }

    @Test
    void findUpcomingBirthdayBy_캐싱_테스트() {
        assertThat(cacheManager.getCache("birthday").get(Month.JUNE)).isNull();

        userInternalService.findUpcomingBirthdayBy(Month.JUNE);

        assertThat(cacheManager.getCache("birthday").get(Month.JUNE)).isNotNull();
    }

    @Test
    @DisplayName("새로운 유저가 등록되면 캐싱 되어 있는 생일 유저들을 삭제한다")
    void findUpcomingBirthdayBy_캐싱_삭제_테스트() {
        assertNull(cacheManager.getCache("birthday").get(Month.JUNE));
        userInternalService.findUpcomingBirthdayBy(Month.JUNE);

        assertNotNull(cacheManager.getCache("birthday").get(Month.JUNE));
        userInternalService.update(1L, "test", LocalDate.of(1995, 6, 8));

        assertNull(cacheManager.getCache("birthday").get(Month.JUNE));
    }
}

