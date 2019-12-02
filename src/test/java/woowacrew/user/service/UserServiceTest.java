package woowacrew.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.user.domain.User;
import woowacrew.user.domain.UserResponseDto;
import woowacrew.user.domain.UserUpdateDto;
import woowacrew.user.service.exception.NotExistUserException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserInternalService userInternalService;

    @InjectMocks
    private UserService userService;

    @Test
    void 정상적으로_유저를_찾는다() {
        User user = new User("test", "test");
        when(userInternalService.findById(anyLong())).thenReturn(user);

        assertThat(userService.findById(1L).getUserId()).isEqualTo("test");
    }

    @Test
    void 유저를_찾지못하면_예외가_발생한다() {
        when(userInternalService.findById(anyLong())).thenThrow(NotExistUserException.class);

        assertThatThrownBy(() -> userService.findById(1L)).isInstanceOf(NotExistUserException.class);
    }

    @Test
    void 정상적으로_유저의_정보를_업데이트한다() {
        User user = new User("test", "test");

        when(userInternalService.findById(anyLong())).thenReturn(user);

        UserUpdateDto userUpdateDto = new UserUpdateDto("test nickname", "1995-06-08");
        UserResponseDto updateUser = userService.update(1L, userUpdateDto);

        assertThat(updateUser.getUserId()).isEqualTo("test");
        assertThat(updateUser.getNickname()).isEqualTo("test nickname");
        assertThat(updateUser.getBirthday()).isEqualTo(LocalDate.of(1995, 6, 8));
    }
}