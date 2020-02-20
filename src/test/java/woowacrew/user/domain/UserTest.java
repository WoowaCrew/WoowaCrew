package woowacrew.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacrew.common.service.FieldSetter;
import woowacrew.degree.domain.Degree;
import woowacrew.user.domain.exception.ForbiddenUserException;
import woowacrew.user.domain.exception.NotExistNicknameException;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    @DisplayName("정상적으로 유저의 추가 정보인 닉네임, 생년월일을 업데이트 한다.")
    void updateUserInfo() {
        LocalDate birthday = LocalDate.of(1995, 6, 8);
        String nickname = "test nickname";
        User user = new User("test", new Degree());

        user.updateUserInfo(nickname, birthday);

        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getBirthday()).isEqualTo(birthday);
    }

    @Test
    @DisplayName("생년월일이 입력되지 않아도 정상적으로 동작한다.")
    void updateUserInfo_ifBirthdayIsEmpty_thenSuccess() {
        User user = new User("test", new Degree());

        assertDoesNotThrow(() -> user.updateUserInfo("nickname", null));
    }

    @Test
    @DisplayName("닉네임이 null 이거나 빈 값인 경우 예외가 발생한다.")
    void updateUserInfoFail() {
        LocalDate birthday = LocalDate.of(1995, 6, 8);
        User user = new User("test", new Degree());

        assertThrows(NotExistNicknameException.class, () -> user.updateUserInfo(null, birthday));
        assertThrows(NotExistNicknameException.class, () -> user.updateUserInfo("", birthday));
    }

    @Test
    @DisplayName("관리자는 유저의 Role과 기수를 업데이트 할 수 있다.")
    void updateUserRole() {
        User admin = new User("admin", UserRole.ROLE_ADMIN, new Degree());
        User user = new User("user", new Degree());

        Degree updateDegree = new Degree();
        updateDegree.update(1);
        user.updateByAdmin(admin, UserRole.ROLE_CREW, updateDegree);

        assertThat(user.getRole()).isEqualTo(UserRole.ROLE_CREW);
        assertThat(user.getDegree().getDegreeNumber()).isEqualTo(updateDegree.getDegreeNumber());
    }

    @Test
    @DisplayName("권한이 없는 유저가 업데이트를 하는 경우 ForbiddenUserException가 발생한다.")
    void updateUserRole_ifUserRoleIsCrew_thenForbiddenUserException() {
        User admin = new User("admin", UserRole.ROLE_CREW, new Degree());
        User user = new User("user", new Degree());

        Degree updateDegree = new Degree();
        updateDegree.update(1);
        assertThatThrownBy(() -> user.updateByAdmin(admin, UserRole.ROLE_CREW, updateDegree))
                .isInstanceOf(ForbiddenUserException.class);
    }

    @Test
    @DisplayName("동기인지 확인한다")
    void isSameDegree() {
        Degree degree = new Degree();
        FieldSetter.set(degree, "id", 1L);

        User user1 = new User("oauthId", degree);
        User user2 = new User("oauthId2", degree);

        assertThat(user1.isSameDegree(user2)).isTrue();
    }

    @Test
    void isUpcomingBirthday() {
        User user = new User("test", new Degree());
        user.updateUserInfo("test", LocalDate.of(1995, 6, 8));

        assertTrue(user.isUpcomingBirthday(Month.JUNE));
        assertFalse(user.isUpcomingBirthday(Month.JULY));
    }

    @Test
    void isBirthday() {
        User user = new User("test", new Degree());
        user.updateUserInfo("test", LocalDate.of(1995, 6, 8));

        assertTrue(user.isBirthday(LocalDate.of(2020, 6, 8)));
        assertFalse(user.isBirthday(LocalDate.of(2020, 6, 7)));
    }
}