package woowacrew.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.degree.domain.Degree;
import woowacrew.user.domain.User;
import woowacrew.user.dto.UserResponseDto;
import woowacrew.user.dto.UserUpdateDto;
import woowacrew.user.service.exception.InvalidBirthdayException;
import woowacrew.user.service.exception.NotExistUserException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserInternalService userInternalService;

    @InjectMocks
    private UserService userService;

    @Test
    void 정상적으로_유저를_찾는다() {
        User user = new User("test", new Degree());
        when(userInternalService.findById(anyLong())).thenReturn(user);

        assertThat(userService.findById(1L).getOauthId()).isEqualTo("test");
    }

    @Test
    void 유저를_찾지못하면_예외가_발생한다() {
        when(userInternalService.findById(anyLong())).thenThrow(NotExistUserException.class);

        assertThatThrownBy(() -> userService.findById(1L)).isInstanceOf(NotExistUserException.class);
    }

    @Test
    void 정상적으로_유저의_정보를_업데이트한다() {
        User user = new User("test", new Degree());
        user.updateUserInfo("test nickname", LocalDate.of(1995, 6, 8));

        when(userInternalService.update(anyLong(), anyString(), any())).thenReturn(user);

        UserUpdateDto userUpdateDto = new UserUpdateDto("test nickname", "1995-06-08");
        UserResponseDto updateUser = userService.update(1L, userUpdateDto);

        assertThat(updateUser.getOauthId()).isEqualTo("test");
        assertThat(updateUser.getNickname()).isEqualTo("test nickname");
        assertThat(updateUser.getBirthday()).isEqualTo(LocalDate.of(1995, 6, 8));
    }

    @Test
    void birthday_날짜가_오늘_날짜_이후인_경우_예외가_발생한다() {
        String birthdayAfterMonth = LocalDate.now()
                .plusMonths(1)
                .format(DateTimeFormatter.ISO_DATE);
        UserUpdateDto userUpdateDto = new UserUpdateDto("test nickname", birthdayAfterMonth);

        assertThatThrownBy(() -> userService.update(1L, userUpdateDto))
                .isInstanceOf(InvalidBirthdayException.class);
    }

    @Test
    void birthday_날짜가_날짜_범위에_벗어난_경우_예외가_발생한다() {
        UserUpdateDto userUpdateDto = new UserUpdateDto("test nickname", "1995-01-32");

        assertThatThrownBy(() -> userService.update(1L, userUpdateDto))
                .isInstanceOf(InvalidBirthdayException.class);
    }

    @Test
    void birthday_월이_범위에_벗어난_경우_예외가_발생한다() {
        UserUpdateDto userUpdateDto = new UserUpdateDto("test nickname", "1995-13-01");

        assertThatThrownBy(() -> userService.update(1L, userUpdateDto))
                .isInstanceOf(InvalidBirthdayException.class);
    }
}