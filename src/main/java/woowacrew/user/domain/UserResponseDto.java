package woowacrew.user.domain;

import java.time.LocalDate;

public class UserResponseDto {
    private Long id;
    private String userId;
    private String nickname;
    private LocalDate birthday;

    private UserResponseDto() {
    }

    public UserResponseDto(Long id, String userId, String nickname, LocalDate birthday) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "UserResponseDto{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
