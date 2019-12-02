package woowacrew.oauth.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserContext;
import woowacrew.user.domain.UserRepository;
import woowacrew.user.domain.UserResponseDto;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginService loginService;

    @Test
    @DisplayName("존재하지 않는 유저라면, Oauth로 전달 받은 유저 ID, URL로만 유저를 생성한다.")
    void saveUser() {
        UserContext userContext = new UserContext("123", "123");

        when(userRepository.findByUserId(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(new User("123", "123"));

        UserResponseDto actual = loginService.save(userContext);

        assertThat(actual.getUserId()).isEqualTo("123");
        assertNull(actual.getNickname());
        assertNull(actual.getBirthday());
    }

    @Test
    @DisplayName("유저가 저장되어 있으면 해당 유저 정보 반환한다.")
    void save_ifUserSaved_thenUserResponseDtoBySavedUser() {
        UserContext userContext = new UserContext("123", "123");
        User user = new User("123", "123");
        user.updateUserInfo("hyojae", LocalDate.of(1995, 6, 8));

        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));

        UserResponseDto actual = loginService.save(userContext);

        assertThat(actual.getUserId()).isEqualTo("123");
        assertThat(actual.getNickname()).isEqualTo("hyojae");
        assertThat(actual.getBirthday()).isEqualTo(LocalDate.of(1995, 6, 8));
    }
}