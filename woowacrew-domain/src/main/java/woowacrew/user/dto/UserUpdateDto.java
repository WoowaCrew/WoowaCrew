package woowacrew.user.dto;

public class UserUpdateDto {
    private String nickname;
    private String birthday;

    public UserUpdateDto(String nickname, String birthday) {
        this.nickname = nickname;
        this.birthday = birthday;
    }

    public String getNickname() {
        return nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return "UserUpdateDto{" +
                "nickname='" + nickname + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
