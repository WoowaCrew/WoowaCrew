package woowacrew.user.domain;

import java.time.LocalDate;

public class UserResponseDto {
    private String userId;
    private String nickname;
    private LocalDate birthday;

    public UserResponseDto(String userId, String nickname, LocalDate birthday) {
        this.userId = userId;
        this.nickname = nickname;
        this.birthday = birthday;
    }

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}
