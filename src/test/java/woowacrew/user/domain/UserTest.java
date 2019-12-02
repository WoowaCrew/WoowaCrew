package woowacrew.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacrew.user.domain.exception.NotExistNicknameException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {
    @Test
    @DisplayName("정상적으로 유저의 추가 정보인 닉네임, 생년월일을 업데이트 한다.")
    void updateUserInfo() {
        LocalDate birthday = LocalDate.of(1995, 6, 8);
        String nickname = "test nickname";
        User user = new User("test", "test");

        user.updateUserInfo(nickname, birthday);

        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getBirthday()).isEqualTo(birthday);
    }

    @Test
    @DisplayName("생년월일이 입력되지 않아도 정상적으로 동작한다.")
    void updateUserInfo_ifBirthdayIsEmpty_thenSuccess() {
        User user = new User("test", "test");

        assertDoesNotThrow(() -> user.updateUserInfo("nickname", null));
    }

    @Test
    @DisplayName("닉네임이 null 이거나 빈 값인 경우 예외가 발생한다.")
    void updateUserInfoFail() {
        LocalDate birthday = LocalDate.of(1995, 6, 8);
        User user = new User("test", "test");

        assertThrows(NotExistNicknameException.class, () -> user.updateUserInfo(null, birthday));
        assertThrows(NotExistNicknameException.class, () -> user.updateUserInfo("", birthday));
    }
}