package woowacrew.user.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class UserConverterTest {
    @Test
    void 올바른_유저를_입력했을_때_UserDto가_정상_출력() {
        User user = new User("userId");
        UserContext userContext = UserConverter.userToUserContextDto(user);

        assertThat(userContext.getUserId()).isEqualTo("userId");
    }

    @Test
    void 유저_정보를_정상적으로_반환한다() {
        User user = new User("123");
        user.updateUserInfo("hyojae", LocalDate.of(1995, 6, 8));

        UserResponseDto actual = UserConverter.userToUserResponseDto(user);

        assertThat(actual.getUserId()).isEqualTo("123");
        assertThat(actual.getNickname()).isEqualTo("hyojae");
        assertThat(actual.getBirthday()).isEqualTo(LocalDate.of(1995, 6, 8));
    }
}