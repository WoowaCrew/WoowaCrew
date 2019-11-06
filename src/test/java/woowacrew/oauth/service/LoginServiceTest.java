package woowacrew.oauth.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacrew.user.domain.UserDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginServiceTest {
    @Autowired
    private LoginService loginService;

    @Test
    void 존재하지_않는_유지이면_저장() {
        UserDto userDto = new UserDto("123", "123");
        UserDto result = loginService.save(userDto);
        assertThat(result).isEqualTo(userDto);
    }
}